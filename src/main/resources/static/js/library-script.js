$(document).ready(function() {
    function loadBooks() {
        $.ajax({
            url: '/Book',
            method: 'GET',
            success: function(data) {
                $('#bookGrid').empty();
                data.forEach(book => {
                    $('#bookGrid').append(`
                        <div class="col-md-3 book-card">
                            <img src="${book.coverImageUrl}" alt="${book.title}" class="img-fluid">
                            <h5>${book.title}</h5>
                            <p>${book.author}</p>
                            <p>${book.description}</p>
                            <audio controls>
                                <source src="${book.audioBookUrl}" type="audio/mpeg">
                                Seu navegador não suporta o elemento de áudio.
                            </audio>
                        </div>
                    `);
                });
            },
            error: function() {
                alert('Erro ao carregar os livros.');
            }
        });
    }

    loadBooks();

 $('#addBookForm').on('submit', function(event) {
     event.preventDefault();

     var formData = new FormData();
     formData.append('title', $('#title').val());
     formData.append('author', $('#author').val());
     formData.append('publishingCompany', $('#publishingCompany').val());
     formData.append('year', $('#year').val());
     formData.append('genre', $('#genre').val());
     formData.append('price', $('#price').val());
     formData.append('description', $('#description').val());
     formData.append('publishDate', $('#publishDate').val());

     if ($('#coverImage')[0].files.length > 0) {
         formData.append('coverImage', $('#coverImage')[0].files[0]);
     } else {
         formData.append('coverImageId', $('#coverImageId').val());
     }

     if ($('#audioBook')[0].files.length > 0) {
         formData.append('audioBook', $('#audioBook')[0].files[0]);
     } else {
         formData.append('audioBookId', $('#audioBookId').val());
     }

     $.ajax({
         url: '/Book',
         method: 'POST',
         data: formData,
         processData: false,
         contentType: false,
         success: function() {
             loadBooks();
             alert('Livro adicionado com sucesso!');
             $('#addBookForm')[0].reset();
         },
         error: function() {
             alert('Erro ao adicionar o livro.');
         }
     });
 });
});
