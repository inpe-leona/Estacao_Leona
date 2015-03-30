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
import br.leona.hardware.camera.CameraController;
import br.leona.hardware.file.FileXML;
import br.leona.hardware.model.Servico;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.CannotRealizeException;
import javax.media.NoPlayerException;

/**
 *
 * @author leona
 *
 */

@WebService(serviceName = "ControllerServices")
public class ControllerServices {

    public ControllerServices(){
        IniciarVideo();
        System.out.println("não excluir o construtor"); 
    }
    
    PTZController pantilt = new PTZController();
    CameraController cameraController = new CameraController();

    @WebMethod(operationName = "ResetPantilt")
    public int ResetPantilt() throws InterruptedException {
        return pantilt.resetPantilt();
    }

    @WebMethod(operationName = "desligarPantilt")
    public int Desligar() {
          return pantilt.close();
    }

    /**
     * Operação de movimetação do pantilt azimute e elevação.
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

    public void IniciarVideo() {
        System.out.println("ws- iniciar video");
        try {
            cameraController.iniciarVideo();
        } catch (IOException ex) {
            Logger.getLogger(ControllerServices.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoPlayerException ex) {
            Logger.getLogger(ControllerServices.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CannotRealizeException ex) {
            Logger.getLogger(ControllerServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Operação para ligar a camera
     */
    @WebMethod(operationName = "LigarCamera")
    public int LigarCamera() {
          System.out.println("ws - ligar camera");
        int result = pantilt.cameraOn();
        if (result != 0) {
            System.out.println("ERRO ao LIGAR CAMERA");
        }
        return 1;
    }

    /**
     * Operação para desligar a camera
     */
    @WebMethod(operationName = "DesligarCamera")
    public int DesligarCamera() {
           System.out.println("ws - desligar camera");
        int result = pantilt.cameraOff();
        if (result != 0) {
            System.out.println("ERRO ao DESLIGAR CAMERA");
        }
        return result;
    }

    /**
     * Operação para iniciar a captura das imagens
     */
    @WebMethod(operationName = "IniciarCaptura")
    public void IniciarCaptura() throws IOException, NoPlayerException, CannotRealizeException {
      System.out.println("ws - iniciar captura");
        cameraController.iniciarCaptura();
    }

    /**
     * Operação para parar a captura das imagens
     */
    @WebMethod(operationName = "PararCaptura")
    public void PararCaptura() throws IOException, NoPlayerException, CannotRealizeException {
        System.out.println("ws - parar captura");
        cameraController.pararCaptura();
    }

    @WebMethod(operationName = "retornarNomesFotos")
    public List<String> retornarNomesFotos() {
        List<String> listS = new ArrayList<>();
        // diretório que será listado.  
        File baseFolder = new File("C:\\ProjetoLeona\\Evento11122014101708");

        // obtem a lista de arquivos  
        File[] files = baseFolder.listFiles();
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            String name = file.getName();
            listS.add(name);
            System.out.println(name);

        }
        return listS;
    }

    @WebMethod(operationName = "retornarListaStatus")
    public List<Servico> retornarListaStatus() {
        List<Servico> list = new ArrayList<>();
        FileXML fileXML = new FileXML();
        Servico servico = fileXML.readFile("c:/hardware/serialPort.xml");
        list.add(servico);
        return list;
    }

}
