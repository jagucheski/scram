function printDiv(divID)  
{
    var conteudo = document.getElementById(divID).innerHTML;  
    var win = window.open("", "ImpressaoEvolucao", "height=400,width=600");  
    win.document.write(conteudo);  
    win.print();  
    win.close();  
} 