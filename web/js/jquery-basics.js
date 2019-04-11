// This file is from the jQuery tutorials at 
// http://courses.coreservlets.com/Course-Materials/jquery.html

$(function() {
    $("#button1").click(randomizeHeadings);
    $("#button2").click(revertHeadings);
});

function randomizeHeadings() {
  $("h3").each(setRandomStyle);
  $("h3.green").hide("slow");
}

function setRandomStyle() {
  $(this).addClass(randomStyle());
}

function revertHeadings() {
  $("h3.green").show("slow");
  $("h3").removeClass("red")
         .removeClass("yellow")
         .removeClass("green");
}

function randomStyle() {
  var styles = ["red", "yellow", "green"];
  return(randomElement(styles));
}

function randomElement(array) {
  var index = Math.floor(Math.random()*array.length);
  return(array[index]);
}