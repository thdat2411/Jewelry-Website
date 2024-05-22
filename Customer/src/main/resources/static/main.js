(function ($) {
    "use strict";

    new WOW().init();

    //navbar cart
    $(".cart_link > a").on("click", function () {
        $(".mini_cart").addClass("active");
    });

    $(".mini_cart_close > a").on("click", function () {
        $(".mini_cart").removeClass("active");
    });


    $(".slider_area").owlCarousel({
        animateOut: "fadeOut",
        autoplay: true,
        loop: true,
        nav: false,
        autoplayTimeout: 6000,
        items: 1,
        dots: true,
    });
    $(".product_column3").slick({
        centerMode: true,
        centerPadding: "0",
        slidesToShow: 5,
        arrows: true,
        rows: 2,
        prevArrow:
            '<button class="prev_arrow"><i class="ion-chevron-left"></i></button>',
        nextArrow:
            '<button class="next_arrow"><i class="ion-chevron-right"></i></button>',
        responsive: [
            {
                breakpoints: 400,
                settings: {
                    slidesToShow: 1,
                    slidesToScroll: 1,
                },
            },
            {
                breakpoints: 768,
                settings: {
                    slidesToShow: 2,
                    slidesToScroll: 2,
                },
            },
            {
                breakpoints: 992,
                settings: {
                    slidesToShow: 3,
                    slidesToScroll: 3,
                },
            },
            {
                breakpoints: 1200,
                settings: {
                    slidesToShow: 4,
                    slidesToScroll: 4,
                },
            },
        ],
    });
    //for tooltip
    $('[data-toggle="tooltip"]').tooltip();

    //tooltip active
    $(".action_links ul li a, .quick_button a").tooltip({
        animated: "fade",
        placement: "top",
        container: "body",
    });

    $(".blog_column3").owlCarousel({
        autoplay: true,
        loop: true,
        nav: true,
        autoplayTimeout: 5000,
        items: 3,
        dots: false,
        margin: 30,
        navText: [
            '<ion-icon name="chevron-back-outline"></ion-icon>',
            '<ion-icon name="chevron-forward-outline"></ion-icon>',
        ],
        responsiveClass: true,
        responsive: {
            0: {
                items: 1,
            },
            768: {
                items: 2,
            },
            992: {
                items: 3,
            },
        },
    });
    //navactive responsive
    $(".product_navactive").owlCarousel({
        autoplay: false,
        loop: true,
        nav: true,
        items: 4,
        dots: false,
        navText: [
            '<i class="ion-chevron-left arrow-left"></i>',
            '<i class="ion-chevron-right arrow-right"></i>',
        ],
        responsiveClass: true,
        responsive: {
            0: {
                items: 1,
            },
            250: {
                items: 2,
            },
            480: {
                items: 3,
            },
            768: {
                items: 4,
            },
        },
    });

    $(".modal").on("shown.bs.modal", function (e) {
        $(".product_navactive").resize();
    });

    $(".product_navactive a").on("click", function (e) {
        e.preventDefault();
        var $href = $(this).attr("href");
        $(".product_navactive a").removeClass("active");
        $(this).addClass("active");
        $(".product-details-large .tab-pane").removeClass("active show");
        $(".product-details-large " + $href).addClass("active show");
    });
})(jQuery);
function change_image(image){

    var container = document.getElementById("main-image");

    container.src = image.src;
}



document.addEventListener("DOMContentLoaded", function(event) {







});