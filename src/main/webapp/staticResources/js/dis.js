/**
 * Notification bar
 */
$(document).ready(function(){
    $(".alert")
        .delay(2000)
        .hide('slide', {direction: 'up' },
            1800, function(){});
});

/**
 * Employees page
 */

// /**
//  * Switch additional employee filters
//  */
// $('#employees-additional-filters').on('change', function() {
//     if($(this).children(":selected").attr("id") === 'option-1') {
//         $('#free-guides').removeClass('hidden');
//         $('#free-guides').addClass('visible');
//     } else {
//         $('#free-guides').removeClass('visible');
//         $('#free-guides').addClass('hidden');
//     }
// });

/**
 * Exhibits page
 */

/**
 * Switch exhibit filters
 */
$('#exhibit-additional-filters').on('change', function() {
    if($(this).children(":selected").attr("id") === 'option-1') {
        $('#by-title').removeClass('hidden');
        $('#by-title').addClass('visible');

        $('#by-independence').addClass('hidden');

        $('#by-audience').addClass('hidden');
    } else if($(this).children(":selected").attr("id") === 'option-2') {
        $('#by-independence').removeClass('hidden');
        $('#by-independence').addClass('visible');

        $('#by-title').addClass('hidden');

        $('#by-audience').addClass('hidden');
    } else if($(this).children(":selected").attr("id") === 'option-3') {
        $('#by-audience').removeClass('hidden');
        $('#by-audience').addClass('visible');

        $('#by-title').addClass('hidden');

        $('#by-independence').addClass('hidden');
    } else {
        $('#by-title').addClass('hidden');
        $('#by-independence').addClass('hidden');
        $('#by-audience').addClass('hidden');
    }
});

/**
 * Add employee page
 */

/**
 * Show audience input only if employee position is an Audience manager
 */
$('#employee-positions').on('change', function() {
    if($(this).children(":selected").attr("id") === 'option-2') {
        // $('#employee-is-am').removeClass('hidden');
        // $('#employee-is-am').addClass('visible');
        $('#employee-is-am').css('display', 'block');
    } else {
        // $('#employee-is-am').removeClass('visible');
        // $('#employee-is-am').addClass('hidden');
        $('#employee-is-am').css('display', 'none');
    }
});


$('#select-book').on('change',function() {
    var bookId = this.value;
    console.log("bookId", bookId);
    $(this).parent().find('.book-instance-wrapper').remove();
    $.get('http://localhost:8080/library/books-instances/' + bookId , function (data) {
        console.log(data);
        $('.books-group').append(data);
    });
});


