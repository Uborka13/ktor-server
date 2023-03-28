package hu.urban.david

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.songRouting() {
    route("/song") {
        get {
            if (songs.isNotEmpty()) {
                call.respond(songs)
            } else {
                call.respondText("No songs", status = HttpStatusCode.OK)
            }
        }
        get("{id?}") {
            val id = call.parameters["id"] ?: return@get call.respondText(
                text = "Missing id",
                status = HttpStatusCode.BadRequest
            )
            val song = songs.find { song -> song.id == id } ?: return@get call.respondText(
                text = "No song with id: $id",
                status = HttpStatusCode.NotFound
            )
            call.respond(song)
        }
        post {
            val song = call.receive<Song>()
            songs.add(song)
            call.respondText(
                text = "Song added",
                status = HttpStatusCode.Created
            )
        }
        delete {
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
            if (songs.removeIf { song -> song.id == id }) {
                call.respondText("Song removed", status = HttpStatusCode.Accepted)
            } else {
                call.respondText("Not Found", status = HttpStatusCode.NotFound)
            }
        }
    }
}