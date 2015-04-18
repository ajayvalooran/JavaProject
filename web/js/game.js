/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var answer = "";
var score = 0;
var stage = 0;

function store(option) {
    answer = option;
    console.log('Answer: ' + answer);
}

function check(option) {
    if (option === answer) {

        if (parseInt(sessionStorage.getItem("userScore")) < 10 || sessionStorage.getItem("stage") === null) {
            console.log("Correct answer");
            //window.alert("Correct answer");
            score = score + 1;
            console.log(score + " :user score");
            if (sessionStorage.getItem("userScore") === 0 || sessionStorage.getItem("userScore") === null) {
                console.log("inside session null");
                sessionStorage.setItem("userScore", 1);
                sessionStorage.setItem("stage", 1);
            }
            else {
                console.log("inside else");
                score = sessionStorage.getItem("userScore");
                score = parseInt(score) + 1;
                sessionStorage.setItem("userScore", score);
                stage = sessionStorage.getItem("stage");
                stage = parseInt(stage) + 1;
                sessionStorage.setItem("stage", stage);
            }
            location.reload();
        }
        else {
            var high = 0;
            $.get('./play/high', function(data) {
                high = parseInt(data);

            });
            if (high < parseInt(sessionStorage.getItem("userScore"))) {
                //var data = parseInt(sessionStorage.setItem("userScore"));
                console.log("Score" + sessionStorage.getItem("userScore"));
                $.ajax({
                    url: "./play/high",
                    dataType: "json",
                    contentType: 'application/json; charset=UTF-8',
                    data: JSON.stringify({"score": parseInt(sessionStorage.getItem("userScore"))}),
                    method: "post",
                    success: true
                });

            }

            var option = prompt("You Won the Game! Like to play another game? yes/no");

            if (option === "yes") {
                sessionStorage.setItem("stage", 1);
                sessionStorage.setItem("userScore", 0)
                location.reload();
            }
            else {
                sessionStorage.setItem("stage", 1);
                sessionStorage.setItem("userScore", 0)
                window.location.href = "./Profile.html";
            }
        }


    }
    else {
        console.log("Wrong anser");
        //window.alert("Wrong answer");
        var high = 0;

        $.get('./play/high', function(data) {
            high = parseInt(data);

        });
        if (high < parseInt(sessionStorage.getItem("userScore"))) {
            //var data = parseInt(sessionStorage.setItem("userScore"));
            console.log("Score" + sessionStorage.getItem("userScore"));
            $.ajax({
                url: "./play/high",
                dataType: "json",
                contentType: 'application/json; charset=UTF-8',
                data: JSON.stringify({"score": parseInt(sessionStorage.getItem("userScore"))}),
                method: "post",
                success: true
            });

        }



        sessionStorage.setItem("userScore", 0);
        var option = prompt("Game Over! Like to play another game? yes/no");

        if (option === "yes") {
            sessionStorage.setItem("stage", 1);
            location.reload();
        }
        else {
            sessionStorage.setItem("stage", 1);
            window.location.href = "./Profile.html";
        }


    }
}

$(document).ready(function() {

    console.log('inside script');

    //$("#score").text(sessionStorage.getItem("userScore"));
    $.getJSON('./play/play',
            function(data) {
                //$("#1").val("Hello");
                $("#1").val(data.opt1);
                $("#2").val(data.opt2);
                $("#3").val(data.opt3);
                $("#4").val(data.opt4);
                $("#image").attr("src", "image/" + data.answer + ".png");
                store(data.answer);
                $("#score").text(sessionStorage.getItem("userScore"));
            });


    $("#1").click(function() {
        check($("#1").val());

    });
    $("#2").click(function() {
        check($("#2").val());

    });
    $("#3").click(function() {
        check($("#3").val());

    });
    $("#4").click(function() {
        check($("#4").val());

    });

});







