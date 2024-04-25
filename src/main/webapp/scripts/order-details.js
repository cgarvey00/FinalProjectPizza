$(document).ready(function () {
    $('.orderinfo').click(function () {
        var orderId = $(this).data('id');
        $.ajax({
            url: 'controller?action=get-order-details',
            type: 'POST',
            data: {orderId: orderId},
            success: function (response) {
                $('.modal-body').html(response);
                $('#orderDetails').modal('show');
            }
        });
    });
});