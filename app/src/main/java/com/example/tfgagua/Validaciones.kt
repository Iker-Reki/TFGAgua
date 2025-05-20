package com.example.tfgagua

import com.example.tfgagua.conexion.RetrofitClient

class Validaciones {

    //TODO: validaciones que sean numericos

    /**
     * Valida si los valores coinciden
     * @param contra1 Primer valor
     * @param contra2 Segundao valor
     * @return false si esta vacio o no coinciden
     */
    fun coinciden(valor1: String, valor2: String): Boolean {
        var resultado : Boolean

        // Verificar que no estén vacías
        if (valor1.isEmpty() || valor2.isEmpty()) {
            resultado = false

            // Verificar que coincidan
        } else if (valor1 != valor2) {
            resultado = false

        } else {
            resultado = true

        }
        return resultado
    }

    /**
     * Valida si un String es nulo o esta vacio
     * @param valor Valor a validar
     * @return true si es nulo o vacío, false si tiene contenido
     */
    fun isBlankOrNull(valor: String?): Boolean {
        return valor.isNullOrBlank()
    }

    /**
     * Valida el formato de un email
     * @param email Email a validar
     * @return false si existe o no esta relleno
     */
    fun validarEmail(email: String?): Boolean {
        var resultado : Boolean = true

        val emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$".toRegex()

        if (isBlankOrNull(email)) {
            resultado = false


        }else if (!email!!.matches(emailRegex)) {
            false
        } else if( !existeCorreo(email)){
             resultado = true
        }

        return resultado
    }
    /**
     * Comprueba si el correo que existe en la BD
     * @param correo a comprobar
     * @return true si el correo existe o false si no existe
     */
    private fun existeCorreo(email: String): Boolean {
        return try {
            val response = RetrofitClient.instancia.existCorreo(email)
            val resultado = response.body()?.count ?: 0

            if (resultado > 0) {
                true  // Existe
            } else {
                false // No existe
            }
        } catch (e: Exception) {
            true // En caso de error, pone que existe para que no se añada
        }
    }





    /*
    fun validarPlantilla(contrasena: String): Boolean {
        // Mínimo 8 caracteres, al menos 1 mayúscula, 1 número y 1 carácter especial
        val pattern = Pattern.compile("^(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}\$")
        return pattern.matcher(contrasena).matches()
    }
    */
}