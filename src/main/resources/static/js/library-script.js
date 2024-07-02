$(document).ready(function() {
    function loadBooks() {
        $.ajax({
            url: '/Book',
            method: 'GET',
            success: function(data) {
                $('#bookGrid').empty();
                data.forEach(book => {
                    $('#bookGrid').append(`
                        <div class="col-md-3 book-card" data-id="${book.id}">
                            <img src="${book.coverImageUrl}" alt="${book.title}" class="img-fluid">
                            <h5>${book.title}</h5>
                            <p>${book.author}</p>
                            <audio controls>
                                <source src="${book.audioBookUrl}" type="audio/mpeg">
                            </audio>
                        </div>
                    `);
                });

                $('.book-card').on('click', function() {
                    const bookId = $(this).data('id');
                    showBookDetails(bookId);
                });
            },
            error: function() {
                alert('Erro ao carregar os livros.');
            }
        });
    }

  function showBookDetails(bookId) {
      $.ajax({
          url: `/Book/${bookId}`,
          method: 'GET',
          success: function(data) {
          console.log(data.audioBookUrl , "AQUIII A URL DO AUDIO");
              $('#bookDetailModal .modal-title').text(data.title);
              $('#bookDetailModal .author').text(data.author);
              $('#bookDetailModal .publishingCompany').text(data.publishingCompany);
              $('#bookDetailModal .year').text(data.year);
              $('#bookDetailModal .description').text(data.description);
              $('#bookDetailModal .genre').text(data.genre);
              $('#bookDetailModal .price').text(data.price);
              $('#bookDetailModal .publishDate').text(data.publishDate);
              $('#bookDetailModal .coverImage').attr('src', data.coverImageUrl);
              $('#bookDetailModal .audioBook').attr('src', data.audioBookUrl);
              $('#editBookBtn').data('id', bookId);
              $('#deleteBookBtn').data('id', bookId);

              $('#bookDetailModal').modal('show');
          },
          error: function() {
              alert('Erro ao recuperar detalhes do livro.');
          }
      });
  }

    loadBooks();

    function formatPublishDate(dateStr) {
        const [day, month, year] = dateStr.split("/");
        return `${year}-${month}-${day}T00:00:00.000Z`;
    }

    $("#addBookForm").submit(function(event) {
        event.preventDefault();

        var formData = new FormData();
        formData.append("title", $("#title").val());
        formData.append("author", $("#author").val());
        formData.append("publishingCompany", $("#publishingCompany").val());
        formData.append("year", $("#year").val());
        formData.append("genre", $("#genre").val());
        formData.append("price", $("#price").val());
        formData.append("description", $("#description").val());
        formData.append("publishDate", formatPublishDate($("#publishDate").val()));

        var coverImage = $("#coverImage")[0].files[0];
        var audioBook = $("#audioBook")[0].files[0];

        if (coverImage) {
            formData.append("coverImage", coverImage);
        }

        if (audioBook) {
            formData.append("audioBook", audioBook);
        }

        $.ajax({
            url: "http://localhost:8080/Book",
            type: "POST",
            data: formData,
            processData: false,
            contentType: false,
            success: function(response) {
                alert("Livro adicionado com sucesso!");
                console.log(response);
                loadBooks();
            },
            error: function(jqXHR, textStatus, errorThrown) {
                alert("Erro ao adicionar o livro: " + textStatus);
                console.error("Erro: " + errorThrown);
            }
        });
    });

    $('#deleteBookBtn').on('click', function() {
        var bookId = $(this).data('id');
        if (confirm('Tem certeza que deseja excluir este livro?')) {
            $.ajax({
                url: `/Book/${bookId}`,
                method: 'DELETE',
                success: function() {
                    alert('Livro excluído com sucesso!');
                    $('#bookDetailModal').modal('hide');
                    loadBooks();
                },
                error: function() {
                    alert('Erro ao excluir o livro.');
                }
            });
        }
    });

  $('#editBookBtn').on('click', function() {
        var bookId = $(this).data('id');

        $.ajax({
            url: `/Book/${bookId}`,
            method: 'GET',
            success: function(data) {
                $('#editTitle').val(data.title);
                $('#editAuthor').val(data.author);
                $('#editPublishingCompany').val(data.publishingCompany);
                $('#editYear').val(data.year);
                $('#editGenre').val(data.genre);
                $('#editPrice').val(data.price);
                $('#editDescription').val(data.description);
                $('#editPublishDate').val(data.publishDate);

                $('#editBookModal').data('id', bookId);

                $('#editBookModalLabel').text('Editar Livro');
                $('#editBookFormSubmitButton').text('Salvar Alterações');

                $('#editBookModal').modal('show');
            },
            error: function() {
                alert('Erro ao recuperar os detalhes do livro para edição.');
            }
        });
    });

    $('#editBookForm').on('submit', function(event) {
        event.preventDefault();

        var bookId = $('#editBookModal').data('id');
        var formData = new FormData(this);

        $.ajax({
            url: `/Book/${bookId}`,
            method: 'PUT',
            data: formData,
            processData: false,
            contentType: false,
            success: function() {
                $('#editBookModal').modal('hide');
                location.reload();
            },
            error: function() {
                alert('Erro ao salvar as alterações.');
            }
        });
    });
});
