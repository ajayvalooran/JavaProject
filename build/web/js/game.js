/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var answer = "";
function store(option) {
      answer = option;  
      console.log('Answer: '+answer);
}

function check(option){
      if(option === answer){
          console.log("Correct answer");
          window.alert("Correct answer");
          location.reload();
      }  
      else{
          console.log("Wrong anser");
          window.alert("Wrong answer");
      }
}

$(document).ready(function() {
    
    console.log('inside script');
    $.getJSON('./play/play',
            function(data) {
                //$("#1").val("Hello");
                $("#1").val(data.opt1);
                $("#2").val(data.opt2);
                $("#3").val(data.opt3);
                $("#4").val(data.opt4);
                $("#image").attr("src", "image/" + data.answer + ".png");
                store(data.answer);
               
            });
            
            
    $("#1").click(function(){
        check($("#1").val());
        
    });
    $("#2").click(function(){
        check($("#2").val());
        
    });
    $("#3").click(function(){
        check($("#3").val());
        
    });
    $("#4").click(function(){
        check($("#4").val());
        
    });
            
});







