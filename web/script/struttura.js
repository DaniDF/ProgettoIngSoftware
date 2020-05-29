/**
 * 
 */

function viewTurni(num)
{
	var but = document.getElementById('turno_add');
	
	but.style.display = (but.style.display == "initial")? "none":"initial";
	
	for(var cont = 0; cont < num; cont++)
	{
		but = document.getElementById('turno' + cont);
		
		but.style.display = (but.style.display == "initial")? "none":"initial";
	}
}