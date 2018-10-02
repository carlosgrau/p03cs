/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.carlosgrau.calculadora;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.carlosgrau.beans.Parametros;

/**
 *
 * @author a021792876p
 */
public class Calculadora extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */

            Gson gson = new Gson();
            String snum1 = request.getParameter("num1").trim();
            String snum2 = request.getParameter("num2").trim();
            String soperador = request.getParameter("operador").trim();
            String exp = "^[0-9][0-9]*";
            Random rand = new Random();

            int aleatorio = (int) Math.round(Math.random() * 5 + 1);

            try {
                if (snum1.matches(exp) && snum2.matches(exp) && soperador.matches(exp)) {
                    Integer num1 = Integer.parseInt(snum1);
                    Integer num2 = Integer.parseInt(snum2);
                    Integer operador = Integer.parseInt(soperador);
                    if (operador <= 4 && operador >= 1) {

                        Parametros param = new Parametros();
                        param.setNum2(num2);
                        param.setOperador(operador);
                        param.setNum1(num1);

                        Integer resultado = null;

                        switch (param.getOperador()) {
                            case 1:
                                resultado = param.getNum1() + param.getNum2();
                                break;
                            case 2:
                                resultado = param.getNum1() - param.getNum2();
                                break;
                            case 3:
                                resultado = param.getNum1() * param.getNum2();
                                break;
                            case 4:
                                if (param.getNum2() == 0) {
                                    response.setStatus(500);
                                    String error = "No puedes dividir entre 0.";
                                    String strJson = gson.toJson(error);
                                    out.print(strJson);
                                } else {
                                    resultado = param.getNum1() / param.getNum2();
                                }
                                break;
                        }
                        try {

                            String strJson = gson.toJson(resultado.toString());
                            TimeUnit.SECONDS.sleep(aleatorio);

                            out.print(strJson);
                        } catch (InterruptedException ex) {
                            Thread.currentThread().interrupt();
                        }

                    } else {
                        response.setStatus(500);
                        String error = "La operación que ha introducido no es correcta.";
                        String strJson = gson.toJson(error);
                        out.print(strJson);
                    }
                } else {
                    throw new IllegalArgumentException();
                }
            } catch (IllegalArgumentException iae) {
                response.setStatus(500);
                String error;
                if (snum1.equals("") || snum2.equals("") || soperador.equals("")) {
                    error = "Los campos no pueden estan en blanco.";

                } else {
                    error = "Debe introducir sólo numeros enteros.";
                }

                String strJson = gson.toJson(error);
                out.print(strJson);

            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
