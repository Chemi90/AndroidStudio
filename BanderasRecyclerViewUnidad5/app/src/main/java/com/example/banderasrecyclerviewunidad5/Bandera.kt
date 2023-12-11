package com.example.banderasrecyclerviewunidad5

/*
 * Data class Bandera, que representa la estructura de un objeto Bandera.
 * Las data classes en Kotlin son una forma concisa de crear clases que solo contienen datos.
 * En este caso, esta clase se utiliza para representar una bandera con sus atributos.
 * @param id Identificador único de la bandera (tipo Int).
 * @param nombre Nombre de la bandera (tipo String).
 * @param imagen URL o referencia a la imagen de la bandera (tipo String).
 */
data class Bandera(
    val id: Int, // Identificador único de la bandera.
    var nombre: String, // Nombre de la bandera. Puede ser modificado.
    val imagen: String // URL o referencia a la imagen de la bandera. No puede ser modificada después de la creación.
)