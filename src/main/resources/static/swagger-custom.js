(function () {
    var TOKEN_KEY = "learn_auth_swagger_token";
    var attempts = 0;
    var maxAttempts = 80;
    var intervalMs = 250;

    function preAuthorize(token) {
        if (!window.ui || typeof window.ui.preauthorizeApiKey !== "function") {
            return false;
        }

        // Support both names if scheme changes across branches.
        window.ui.preauthorizeApiKey("bearerAuth", token);
        window.ui.preauthorizeApiKey("bearer-key", token);
        return true;
    }

    function injectToken() {
        var token = localStorage.getItem(TOKEN_KEY);
        if (!token) {
            return;
        }

        var timer = setInterval(function () {
            attempts += 1;
            if (preAuthorize(token) || attempts >= maxAttempts) {
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
