var CURRENT_URL = window.location.href.split('#')[0].split('?')[0],
    $BODY = $('body'),
    $MENU_TOGGLE = $('#menu_toggle'),
    $SIDEBAR_MENU = $('#sidebar-menu'),
    $SIDEBAR_FOOTER = $('.sidebar-footer'),
    $LEFT_COL = $('.left_col'),
    $RIGHT_COL = $('.right_col'),
    $NAV_MENU = $('.nav_menu'),
    $FOOTER = $('footer');

// Sidebar
$(document).ready(function() {
    // TODO: This is some kind of easy fix, maybe we can improve this
    var setContentHeight = function () {
        // reset height
        //$RIGHT_COL.css('min-height', $(window).height());

        var bodyHeight = $BODY.outerHeight(),
            footerHeight = $BODY.hasClass('footer_fixed') ? -10 : $FOOTER.height(),
            leftColHeight = $LEFT_COL.eq(1).height() + $SIDEBAR_FOOTER.height(),
            contentHeight = bodyHeight < leftColHeight ? leftColHeight : bodyHeight;

        // normalize content
        var right_col_height = $("html").height()-$NAV_MENU.height();

        $RIGHT_COL.css('min-height', Math.max(leftColHeight,right_col_height));
    };

    $SIDEBAR_MENU.find('a').on('click', function(ev) {
        var $li = $(this).parent("li");//当前li
        //当前是否是激活状态
        if ($li.is('.active')) {
            //当前已被选中
            if ($li.parent().is('ul.side-menu')) {
                //如果是父级菜单，切换自己
                $li.find("ul.child_menu").slideToggle(function() {
                    setContentHeight();
                });
            }
            //什么也不做
        }else{
            //点击了非激活状态的菜单，
            if ($li.parent().is('ul.side-menu')) {
                //如果是父级菜单，收起其他菜单
                !$SIDEBAR_MENU.find("li").not($li).find("ul.child_menu").slideUp();
                $('ul:first', $li).slideToggle(function() {
                    setContentHeight();
                });
            }else if ($li.parent().is("ul.child_menu")){
                //如果是子菜单，取消其他激活属性
                $SIDEBAR_MENU.find('li').removeClass('active active-sm current-page');
                //激活当前父级菜单和子菜单
                $li.addClass("current-page")
                $li.parents("li").addClass("active")

            }
        }
    });

    // toggle small or large menu
    $MENU_TOGGLE.on('click', function() {
        if ($BODY.hasClass('nav-md')) {
            $SIDEBAR_MENU.find('li.active ul').hide();
            $SIDEBAR_MENU.find('li.active').addClass('active-sm').removeClass('active');
        } else {
            $SIDEBAR_MENU.find('li.active-sm ul').show();
            $SIDEBAR_MENU.find('li.active-sm').addClass('active').removeClass('active-sm');
        }

        $BODY.toggleClass('nav-md nav-sm');

        setContentHeight();

    });

    // recompute content when resizing
    $(window).smartresize(function(){  
        setContentHeight();
    });

    setContentHeight();

    // fixed sidebar
    if ($.fn.mCustomScrollbar) {
        $('.menu_fixed').mCustomScrollbar({
            autoHideScrollbar: true,
            theme: 'minimal',
            mouseWheel:{ preventDefault: true }
        });
    }
});

