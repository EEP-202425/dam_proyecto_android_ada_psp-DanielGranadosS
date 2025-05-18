object ApiBaseUrlProvider {
    fun getBaseUrl(): String {
        return if (android.os.Build.FINGERPRINT.contains("generic")) {
            "http://10.0.2.2:8080/"
        } else {
            "http://192.168.1.48:8080/"
        }
    }
}