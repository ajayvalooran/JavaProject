/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$('#Login').click(function () {
        var user = $('#username').val();
        var pwd = $('#password').val();
        if (user === '' || pwd === '') {
            $("#err").text("Please enter both username and password.");
        }
        else {
            var url = './webresources/users/' + user + '/' + pwd;
            $.getJSON(url, function (data) {
                if (!jQuery.isEmptyObject(data)) {
                    alert(data);
                   // sessionStorage.setItem("userId", data.id);
                    window.location.href = "user.jsp";
                }
                else {
                    $("#err").text("Login failed.");
                }
            });
        }
    });