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

/**
 *
 * @author leona
 *
 */
@WebService(serviceName = "ControllerServices")
public class ControllerServices {

    PTZController pantilt = new PTZController();

    @WebMethod(operationName = "MoverEsquerda")
    public int moverEsquerda(@WebParam(name = "graus") int graus) {
        return pantilt.left(graus);
    }

    @WebMethod(operationName = "MoverCima")
    public int MoverCima(@WebParam(name = "graus") int graus) {
        return pantilt.up(graus);
    }

    @WebMethod(operationName = "MoverDireita")
    public int MoverDireita(@WebParam(name = "graus") int graus) {
        return pantilt.right(graus);
    }

    @WebMethod(operationName = "MoverBaixo")
    public int MoverBaixo(@WebParam(name = "graus") int graus) {
        //TODO write your implementation code here:
        return pantilt.down(graus);
    }

    @WebMethod(operationName = "LigarDesligarCamera")
    public int LigarDesligarCamera(@WebParam(name = "valor") int valor) {
        return pantilt.camera(valor);

    }

    @WebMethod(operationName = "ResetPantilt")
    public int ResetPantilt() {
        return pantilt.resetPantilt();

    }

}
