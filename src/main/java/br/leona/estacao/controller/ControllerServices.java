/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.leona.estacao.controller;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import br.leona.hardware.controller.PTZController;
import br.leona.hardware.file.FileXML;
import br.leona.hardware.model.Servico;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;  
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author leona
 *
 */

@WebService(serviceName = "ControllerServices")
public class ControllerServices {    
    private static PTZController pantilt; 
    private static List<String> portasRTP; 
    private static List<String> diretorios;
    private static List<Process> processosTrans;
    private static List<Process> processosCapt; 
    
    public ControllerServices(){           
        portasRTP = new ArrayList<String>(); 
        diretorios = new ArrayList<String>(); 
        processosTrans = new ArrayList<Process>();
        processosCapt = new ArrayList<Process>();
        pantilt = new PTZController();   
    }  
    
    /**
    * Operação de executar processo transmissor.
    *
    * @param portaRTP
    * 
    * @return nomeProcesso
    */
    @WebMethod(operationName = "executarTrasnmissor")
    public String executarTrasnmissor(@WebParam(name = "portaRTP") String portaRTP) {  
        int index = processosTrans.size();
        int size = index+1;
        String IP = "150.163.55.230";
        System.out.println("ws - Iniciar Transmissor("+size+") IP"+IP+", porta: "+portaRTP);
        //String aplicativo =  "javaw -jar D:/NetBeansProjects/TransmitterController/dist/TransmitterController.jar "
        //                  + portaRTP; 
        String aplicativo = "python C:\\Users\\Win76\\Documents\\Arch-Server-Polymorphic.py" ;//+IP+" "+portaRTP;
        try { 
            processosTrans.add(Runtime.getRuntime().exec(aplicativo));
        } catch (IOException ex) {
            Logger.getLogger(ControllerServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("start processo: "+processosTrans.get(index).toString());
        portasRTP.add(portaRTP);
        return processosTrans.get(index).toString();        
    }  
    
    /**
    * Operação de matar processo transmissor.
    *
    * @param nomeProcesso
    */
    @WebMethod(operationName = "matarTrasnmissor")
    public String matarTrasnmissor() {
        int size = processosTrans.size();
        int index = size-1;
        String processo = null;
        if(size>0)   {  
            System.out.println("ws - Matar Transmissor("+(size)
                               +") porta: "+portasRTP.get(index));
            processo = processosTrans.get(index).toString();
            processosTrans.get(index).destroy(); 
            processosTrans.remove(index);   
            portasRTP.remove(index);
        }
        else 
            System.out.println("Não existe Transmissor Aberto!");
        return processo;
    }  
    
     /**
    * Operação de executar processo capturar imagens.
    *
    * @param diretorio  
    * 
    * @return nomeProcesso
    */
    @WebMethod(operationName = "executarCaptura")
    public String executarCaptura(){
        int index = processosCapt.size();
        int size = index+1;
        String diretorio = "C:\\ProjetoLeona";
        if(diretorio.contains(" "))
            diretorio = diretorio.subSequence(0, diretorio.indexOf(" ")).toString();        
        
        //String aplicativo = "javaw -jar D:/NetBeansProjects/CaptureController/dist/CaptureController.jar " + diretorio + "_" + size + "_";
        //String aplicativo = "D:/ProjetoLeona/execRecord.bat";   
        String aplicativo = "python C:\\Users\\Win76\\Documents\\RecordV5.py 0";//+diretorio.toString();   
        
        System.out.println("ws - Iniciar Captura(" + size + ") diretorio: " + aplicativo);
        System.out.println("Aplicação: "+aplicativo);
        Process process = null;
       
        try {    
            process = Runtime.getRuntime().exec(aplicativo);
        } catch (IOException ex) {
            Logger.getLogger(ControllerServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        processosCapt.add(process);
        System.out.println("start processo: "+processosCapt.get(index).toString());
        diretorios.add(diretorio);
        return processosCapt.get(index).toString();     
    }
    
    /**
    * Operação de matar processo capturar iamgens.
    *
    * @return nomeProcesso
    */
    @WebMethod(operationName = "matarCaptura")
    public String matarCaptura(){  
        int size = processosCapt.size();
        int index = size-1;
        String processo = null;
        if(size>0){
            processo = processosCapt.get(index).toString(); 
            System.out.println("ws - Matar Captura "+size+": "+processo);                                
            processosCapt.get(index).destroy();
            processosCapt.remove(index);
            diretorios.remove(index);
        }
        else 
            System.out.println("Não existe Gravador Aberto!");
        return processo;
    }
        
    /**
    * Operação de receber coord XYZ dos sensores.
    *
    * @return coordXYZ
    */
    @WebMethod(operationName = "receberCoordXYZ")
    public String receberCoordXYZ() {       
        System.out.println("ws - receberCoordXYZ ");
        String coordXYZ = "999 999 999";//pantilt.receberCoordXYZ();
        System.out.println("lenght CoordXYZ: "+coordXYZ.length());
        System.out.println("ultmo caracter: "+coordXYZ.charAt(coordXYZ.length()-1));
        System.out.println("ws - receberCoordXYZ: "+coordXYZ);      
        return coordXYZ;
    }    
    
    /**
    * Operação de reset pantilt.
    *
    * @return status
    */
    @WebMethod(operationName = "resetPantilt")
    public int resetPantilt()  {
        return pantilt.resetPantilt();
    }

    /**
    * Operação de desligar o pantilit.
    *
    * @return status
    */
    @WebMethod(operationName = "desligarPantilt")
    public int desligarPantilt() {
        return pantilt.close();
        /*return 1;*/
    }

    /**
    * Operação de movimetação do pantilt azimute.
    *
    * @param graus
    * @return status 
    */
    @WebMethod(operationName = "moverAzimute")
    public String moverAzimute(@WebParam(name = "graus") int graus) {
        return Integer.toString(pantilt.calculoAzimuteElevacao(graus, "azimute"));
        /*return "1";*/
    }

    /**
    * Operação de movimetação do pantilt elevação.
    *
    * @param graus
    * @return status 
    */
    @WebMethod(operationName = "moverElevacao")
    public String moverElevacao(@WebParam(name = "graus") int graus) {
        return Integer.toString(pantilt.calculoAzimuteElevacao(graus, "elevacao"));
    }

    /**
    * Operação de ligar camera.
    *
    * @return status 
    */    
    @WebMethod(operationName = "ligarCamera")
    public int ligarCamera() {
        System.out.println("ws - ligar camera");
        int result = pantilt.cameraOn();
        if (result == 0) {
            System.out.println("ERRO ao LIGAR CAMERA");
        }
        return result;
    }

    /**
    * Operação de desligar camera.
    *
    * @return status 
    */
    @WebMethod(operationName = "desligarCamera")
    public int desligarCamera() {
        System.out.println("ws - desligar camera");
        int result = pantilt.cameraOff();
        if (result == 0) {
            System.out.println("ERRO ao DESLIGAR CAMERA");
        }
        return result;
    }
    
    /**
     * Operação de obter lista de status.
     *
     * @return listaStatus 
     */
    @WebMethod(operationName = "retornarListaStatus")
    public List<Servico> retornarListaStatus() {
        List<Servico> list = new ArrayList<>();
        FileXML fileXML = new FileXML();
        Servico arduino = fileXML.readFile("c:/ProjetoLeona/pantilt.xml");
        list.add(arduino);
        Servico camera = fileXML.readFile("c:/ProjetoLeona/camera.xml");
        list.add(camera);
        return list;
    }
    
    /**
     * Operação de obter lista com o nome das fotos.
     *
     * @param diretorio
     * 
     * @return listaNomesFotos
     */
    @WebMethod(operationName = "retornarNomesFotos")
    public List<String> retornarNomesFotos(@WebParam(name = "diretorio") String diretorio) {
        List<String> listS = new ArrayList<>();
        File baseFolder = new File(diretorio);
        File[] files = baseFolder.listFiles();
        for (File file : files) {
            String name = file.getName();
            listS.add(name);
            System.out.println(name);
        }
        return listS;
    }
    
    /**
     * Operação de remover fotos do diretorio.
     *
     * @param diretorio
     */
    @WebMethod(operationName = "removerFotos")
    public void removerFotos(@WebParam(name = "diretorio") String diretorio){
        File dirFile = new File(diretorio); 
        if (dirFile.isDirectory()) { 
            File[] files = dirFile.listFiles();
            for (File file : files) 
                file.delete(); 
        }
    }
    
    /**
     * Operação de remover diretorio das fotos.
     *
     * @param diretorio
     * 
     */
    @WebMethod(operationName = "removerDiretorioFotos")
    public void removerDiretorio(@WebParam(name = "diretorio") String diretorio) {
        File dirFile = new File(diretorio); 
        if (dirFile.isDirectory())
            removerFotos(diretorio);
        dirFile.delete();
    }
    
    
    /**
    * Operação de iniciar transmissao de imagens.
    *
    * @param portaRTP
    * 
    */
    /*@WebMethod(operationName = "iniciarTransmissao")
    public void iniciarTransmissao(@WebParam(name = "portaRTP") String portaRTP) {
      portasRTP.add(portaRTP);        
      System.out.println("ws - iniciar transmissão: "
              +(transControll.size()+1) +", "
              +portaRTP); 
      TransmitterController trans = new TransmitterController();//new Integer(portaRTP).intValue()); 
      trans.startTransmitter(new Integer(portaRTP).intValue());      
      transControll.add(trans);      
    }*/
     
    /**
    * Operação de parar transmissao de imagens.
    *
    */
    /*@WebMethod(operationName = "pararTransmissao")
    public void pararTransmissao() {
      Integer size = transControll.size();
      System.out.println("ws - parar transmissão: "+size.toString());
      if(size>0){
        transControll.get(size-1).stopTransmitter();
        transControll.remove(size-1);  
        portasRTP.remove(size-1);        
      }
      else 
          System.out.println("Não existe Transmissor Aberto!");
    }*/
    
    /**
    * Operação de iniciar capturar imagens.
    * 
    * @param diretorio
    */
    /*@WebMethod(operationName = "iniciarCaptura")
    public void iniciarCaptura(@WebParam(name = "diretorio") String diretorio) {
        final int index = captControll.size();
        int size = index+1;
        diretorio = diretorio.subSequence(0, diretorio.indexOf(" ")).toString();        
        final String dirCaptura = diretorio+"_"+size+"_";
        diretorios.add(dirCaptura);
        System.out.println("ws - iniciar captura: "+dirCaptura);
        captControll.add(new CaptureController(dirCaptura));
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {                 
                captControll.get(index).startCapture();
            }
        });
    }*/

    /**
    * Operação de parar capturar imagens.
    * 
    */
    /*@WebMethod(operationName = "pararCaptura")
    public void pararCaptura() {
        int index = captControll.size()-1;          
        if(index>=0){
            System.out.println("ws - parar captura: "+diretorios.get(index));  
            captControll.get(index).endCapture();//.stopCapture(); //       
            captControll.remove(index);
            diretorios.remove(index);
        } 
        else System.out.println("Não existe Gravador Aberto!");
    } 
    9*/
}
