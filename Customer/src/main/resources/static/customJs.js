function activateMenuItem(event, clickedElement) {
    // Prevent the default link behavior
    event.preventDefault();

    // Remove the 'active-menu' class from all menu items
    var menuItems = document.querySelectorAll('.main-menu li');
    menuItems.forEach(function(item) {
        item.classList.remove('active-menu');
    });

    // Add the 'active-menu' class to the clicked menu item's parent <li>
    clickedElement.parentNode.classList.add('active-menu');

    // Simulate the navigation after a short delay (e.g., 100 milliseconds)
    setTimeout(function() {
        window.location.href = clickedElement.getAttribute('href');
    }, 100);
}
/*---------------------------------------------*/

$(document).ready(function() {
    $('.dropdown-toggle').on('click', function() {
        var dropdownMenu = $(this).next('.dropdown-menu');
        dropdownMenu.toggle(); // Toggle the visibility of the dropdown
    });
});
/*---------------------------------------------*/

$('.js-pscroll').each(function(){
    $(this).css('position','relative');
    $(this).css('overflow','hidden');
    var ps = new PerfectScrollbar(this, {
        wheelSpeed: 1,
        scrollingThreshold: 1000,
        wheelPropagation: false,
    });

    $(window).on('resize', function(){
        ps.update();
    })
});
/*---------------------------------------------*/

$('.js-addcart-detail').each(function(){
    var nameProduct = $(this).parent().parent().parent().parent().find('.js-name-detail').html();
    $(this).on('click', function(){
        swal(nameProduct, "is added to cart !", "success");
    });
});
/*---------------------------------------------*/

$('.gallery-lb').each(function() { // the containers for all your galleries
    $(this).magnificPopup({
        delegate: 'a', // the selector for gallery item
        type: 'image',
        gallery: {
            enabled:true
        },
        mainClass: 'mfp-fade'
    });
});
/*---------------------------------------------*/

$(".js-select2").each(function(){
    $(this).select2({
        minimumResultsForSearch: 20,
        dropdownParent: $(this).next('.dropDownSelect2')
    });
})
/*---------------------------------------------*/
$('.parallax100').parallax100();
/*---------------------------------------------*/
document.addEventListener('DOMContentLoaded', function() {
    // Get a reference to the "Quick View" button
    const buttons = document.querySelectorAll('.quick-view-button');
    const expandImage = document.getElementById('expand-image');
    const dataThumb = document.getElementById('data-thumb');
    // Add a click event listener to the button
    buttons.forEach(function (button) {
        button.addEventListener('click', function () {
            const productName = this.getAttribute('data-product-name');
            const productPrice = this.getAttribute('data-product-price');
            const productDescription = this.getAttribute('data-product-description');
            const productImage = this.getAttribute('data-product-image');
            const productType = this.getAttribute('data-product-type');
            const productQuantity = this.getAttribute('data-product-quantity');
            const productId = this.getAttribute('data-product-id');
            // Update the modal content with the retrieved product data
            document.getElementById('product-name').textContent = productName;
            document.getElementById('product-price').textContent = productPrice;
            document.getElementById('product-description').textContent = productDescription;
            document.getElementById('product-image').src = productImage;
            document.getElementById('product-type').textContent=productType;
            document.getElementById('product-quantity').textContent=productQuantity;
            document.getElementById('product-id').value = productId;
            // Retrieve and set data-thumb attribute
            expandImage.href=productImage;
            dataThumb.src=productImage;
        });
    });
});