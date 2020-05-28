/**
 * 
 */

function change(check)
{
	if(check.checked)
	{
		document.getElementById('struttura').style.fontWeight = "normal";
		document.getElementById('utente').style.fontWeight = "lighter";
	}
	else
	{
		document.getElementById('utente').style.fontWeight = "normal";
		document.getElementById('struttura').style.fontWeight = "lighter";
	}
}