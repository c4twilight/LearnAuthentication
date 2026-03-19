(function () {
    var TOKEN_KEY = "learn_auth_swagger_token";
    var attempts = 0;
    var maxAttempts = 80;
    var intervalMs = 250;

    function injectToken() {
        var token = localStorage.getItem(TOKEN_KEY);
        if (!token) {
            return;
        }

        var timer = setInterval(function () {
            attempts += 1;

            if (window.ui && typeof window.ui.preauthorizeApiKey === "function") {
                window.ui.preauthorizeApiKey("bearerAuth", token);
                clearInterval(timer);
                return;
            }

            if (attempts >= maxAttempts) {
                clearInterval(timer);
            }
        }, intervalMs);
    }

    if (document.readyState === "loading") {
        document.addEventListener("DOMContentLoaded", injectToken);
    } else {
        injectToken();
    }
})();
