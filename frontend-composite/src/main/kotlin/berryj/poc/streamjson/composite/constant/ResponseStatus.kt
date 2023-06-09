package berryj.poc.streamjson.composite.constant

enum class ResponseStatus(val code: String, val message: String) {
    SUCCESS("200-000", "Success"),
    UNEXPECTED_ERROR("500-000", "Unexpected error")
}