function loadLogin()
{$("#mainContent").slideUp(1).delay(1000).slideDown('slow');
}
function loadWelcome(){
    $("#mainContent" ).slideUp(1000,function(){
    $('#panel').show();
			$( "#mainContent" ).slideDown(1000,function(){
			});
    })
}