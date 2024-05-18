import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "users")
class User(
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID = UUID.randomUUID(),

    name: String,
    email: String,
    password: String
) {
    var name: String = name
        private set

    var email: String = email
        private set

    var password: String = password
        private set

    companion object {
        fun create(name: String, email: String, password: String): User {
            return User(name = name, email = email, password = password)
        }
    }
}
