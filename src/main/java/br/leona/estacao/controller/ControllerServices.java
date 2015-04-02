/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.leona.estacao.controller;

import br.leona.hardware.image.CameraController;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import br.leona.hardware.controller.PTZController;
import br.leona.hardware.file.FileXML;
import br.leona.hardware.model.Servico;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author leona
 *
 */

@WebService(serviceName = "ControllerServices")
public class ControllerServices {
    private static PTZController pantilt;   
    private static CameraController cameraControll;
    
    
    public ControllerServices(){                      
        pantilt = new PTZController();               
        cameraControll = new CameraController(1235); 
    }
  
    /**
     * Operação para iniciar a transmissao das imagens
     */
    @WebMethod(operationName = "iniciarTransmissao")
    public void iniciarTransmissao() {
      System.out.println("ws - iniciar transmissão");
      cameraControll.transmit();
    }
     
    /**
     * Operação para parar a transmissao das imagens
     */
    @WebMethod(operationName = "pararTransmissao")
    public void pararTransmissao() {
      System.out.println("ws - parar transmissão");
      cameraControll.stopTransmit();
    }
    
    /**
     * Operação para iniciar a captura das imagens
     */
    @WebMethod(operationName = "iniciarCaptura")
    public void iniciarCaptura()  {
        System.out.println("ws - iniciar captura");
        cameraControll.capture();
    }

    /**
     * Operação para parar a captura das imagens
     */
    @WebMethod(operationName = "pararCaptura")
    public void pararCaptura()  {
        System.out.println("ws - parar captura");        
        cameraControll.stopCapture();
    }

    @WebMethod(operationName = "resetPantilt")
    public int resetPantilt()  {
        return pantilt.resetPantilt();
    }

    @WebMethod(operationName = "desligarPantilt")
    public int desligar() {
        return pantilt.close();
    }

    /**
     * Operação de movimetação do pantilt azimute e elevação.
     *
     * @param graus
     * @return
     */
    @WebMethod(operationName = "moverAzimute")
    public String moverAzimute(@WebParam(name = "graus") int graus) {
        return Integer.toString(pantilt.calculoAzimuteElevacao(graus, "azimute"));
    }

    @WebMethod(operationName = "moverElevacao")
    public String moverElevacao(@WebParam(name = "graus") int graus) {
        return Integer.toString(pantilt.calculoAzimuteElevacao(graus, "elevacao"));
    }

    /**
     * Operação para ligar a camera
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
     * Operação para desligar a camera
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
            
    @WebMethod(operationName = "removerFotos")
    public void removerFotos(@WebParam(name = "diretorio") String diretorio){
        File dirFile = new File(diretorio); 
        if (dirFile.isDirectory()) { 
            File[] files = dirFile.listFiles();
            for (File file : files) { 
                file.delete(); 
            }   
        }
    }
                
    @WebMethod(operationName = "removerDiretorio")
    public void removerDiretorio(@WebParam(name = "diretorio") String diretorio) {
        File dirFile = new File(diretorio); 
        if (dirFile.isDirectory()) {       
                removerFotos(diretorio);
        }
        dirFile.delete();
    }
}
