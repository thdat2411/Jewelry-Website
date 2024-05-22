$('document').ready(function () {
    $('table #editButton').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        $.get(href, function (category) {
            // Set the existing category data in the modal
            $('#idEdit').val(category.categoryId); // Access categoryId
            $('#nameEdit').val(category.categoryName); // Access categoryName

            // Show the modal
            $('#editModal').modal();
        });
    });
});