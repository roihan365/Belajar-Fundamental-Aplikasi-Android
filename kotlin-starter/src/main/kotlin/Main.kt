fun main() {
    val message: String? = null
    message?.apply {
        val length = it.length
        val text = "text length $length"
        println(text)
    }
}