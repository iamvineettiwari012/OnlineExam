/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$('document').ready(function() {
    $('#toggleSidebar').click(function() {
        $('#body-wrap').toggleClass("sidebarActiveView");
        $('#sidebar').toggleClass("sidebar-active");
        $('#btn-icon').toggleClass("fa-align-justify fa-align-left");
    });

    $('#drop1').click(function(){
        $('#drop1').toggleClass("side-nav-active");
        $('#drop1').toggleClass("li-drop-active");
        $("#drop1-sub").toggleClass("self-dropdown-hidden self-dropdown-active");
    });

    $('#drop2').click(function(){
        $('#drop2').toggleClass("side-nav-active");
        $('#drop2').toggleClass("li-drop-active");
        $("#drop2-sub").toggleClass("self-dropdown-hidden self-dropdown-active");
    });
    
    $('#viewProfile').modal('show');


});