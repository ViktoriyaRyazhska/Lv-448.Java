/**
 * Notification bar
 */
$(document).ready(function () {
    $(".alert")
        .delay(2000)
        .hide('slide', {direction: 'up'},
            1800, function () {
    });
});

/**
 * Display active nav item
 */
$(document).ready(function () {
    $('li.active').removeClass('active');
    $('a[href="' + location.pathname + '"]').closest('li').addClass('active');
});

/**
 * Employees page
 */

/**
 * Switch additional employee filters
 */
$('#employees-additional-filters').on('change', function () {
    if ($(this).children(":selected").attr("id") === 'option-1') {
        $('#free-guides').removeClass('hidden');
        $('#free-guides').addClass('visible');
    } else {
        $('#free-guides').removeClass('visible');
        $('#free-guides').addClass('hidden');
    }
});

/**
 * Exhibits page
 */

/**
 * Switch exhibit filters
 */
$('#exhibit-additional-filters').on('change', function () {
    if ($(this).children(":selected").attr("id") === 'option-1') {
        $('#by-author').removeClass('hidden');
        $('#by-author').addClass('visible');

        $('#by-employee').addClass('hidden');

        $('#by-audience').addClass('hidden');
    } else if ($(this).children(":selected").attr("id") === 'option-2') {
        $('#by-employee').removeClass('hidden');
        $('#by-employee').addClass('visible');

        $('#by-author').addClass('hidden');

        $('#by-audience').addClass('hidden');
    } else if ($(this).children(":selected").attr("id") === 'option-3') {
        $('#by-audience').removeClass('hidden');
        $('#by-audience').addClass('visible');

        $('#by-author').addClass('hidden');

        $('#by-employee').addClass('hidden');
    } else {
        $('#by-author').addClass('hidden');
        $('#by-employee').addClass('hidden');
        $('#by-audience').addClass('hidden');
    }
});

/**
 * Add many authors
 */

let counter = 2;
$(document).on('load', function () {
    counter = 2;
});

$('.add-more-authors').click(function () {
    $.get('http://localhost:8080/museum/authors', function (data) {
        $('.author-group').append(
            '<div class="author-select-wrapper">' +
            '<select class="browser-default custom-select author-select mt-3" ' +
            'id="exhibit-author-' + counter + '" name="author-' + counter + '">' +
            data +
            '</select>' +
            '<div class="btn btn-dark ml-2 mt-3 delete-more-authors"><i class="fas fa-minus"></i></div>' +
            '</div>'
        );
        counter ++;
    });
});

$('#new-exhibit-form').delegate('.delete-more-authors', 'click', function () {
    console.log("Clicked");
    $(this).parent().find('.author-select').remove();
    $(this).remove();
});

/**
 * Show/hide material/technique
 */
$(document).ready(function () {
    $('.exhibit-material-group').css('display', 'none');
    $('.exhibit-technique-group').css('display', 'block');
});

$('#exhibit-type').on('change', function () {
    if ($(this).children(":selected").attr('value') === 'SCULPTURE') {
        $('.exhibit-material-group').css('display', 'block');
        $('#exhibit-technique').val('');
        $('.exhibit-technique-group').css('display', 'none');
    } else if ($(this).children(":selected").attr('value') === 'PAINTING') {
        $('.exhibit-technique-group').css('display', 'block');
        $('#exhibit-material').val('');
        $('.exhibit-material-group').css('display', 'none');
    }
});


/**
 * Add employee page
 */

/**
 * Show audience input only if employee position is an Audience manager
 */
$('#employee-positions').on('change', function () {
    if ($(this).children(":selected").attr("id") === 'option-2') {
        $('#employee-is-am').css('display', 'block');
    } else {
        $('#employee-is-am').css('display', 'none');
    }
});


