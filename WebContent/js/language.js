const languageBtn = document.getElementById("languageBtn");

function setLanguage(language) {

    // Save selected language
    localStorage.setItem("selectedLanguage", language);

    // Set page direction
    document.documentElement.lang = language;
    document.documentElement.dir = language === "ar" ? "rtl" : "ltr";

    // Change all text
    const elements = document.querySelectorAll("[data-ar][data-en]");

    elements.forEach(function (element) {

        if (language === "ar") {
            element.textContent = element.getAttribute("data-ar");
        } else {
            element.textContent = element.getAttribute("data-en");
        }

    });

    // Change placeholders
    const inputs = document.querySelectorAll(
        "[data-placeholder-ar][data-placeholder-en]"
    );

    inputs.forEach(function (input) {

        if (language === "ar") {
            input.placeholder = input.getAttribute("data-placeholder-ar");
        } else {
            input.placeholder = input.getAttribute("data-placeholder-en");
        }

    });

    // Change language button
    if (languageBtn) {

        if (language === "ar") {
            languageBtn.textContent = "English";
        } else {
            languageBtn.textContent = "عربي";
        }

    }
}


// Get saved language
let savedLanguage = localStorage.getItem("selectedLanguage");

if (savedLanguage === null) {
    savedLanguage = "ar";
}


// Apply saved language
setLanguage(savedLanguage);


// Change language when button is clicked
if (languageBtn) {

    languageBtn.addEventListener("click", function () {

        let currentLanguage =
            localStorage.getItem("selectedLanguage");

        if (currentLanguage === "ar") {
            setLanguage("en");
        } else {
            setLanguage("ar");
        }

    });

}