/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad Ean (Bogotá - Colombia)
 * Departamento de Tecnologías de la Información y Comunicaciones
 * Licenciado bajo el esquema Academic Free License version 2.1
 * <p>
 * Proyecto Evaluador de Expresiones Postfijas
 * Fecha: Febrero 2021
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
package universidadean.desarrollosw.postfijo;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

/**
 * Esta clase representa una clase que evalúa expresiones en notación polaca o
 * postfija. Por ejemplo: 4 5 +
 */
public class EvaluadorPostfijo {

    /**
     * Realiza la evaluación de la expresión postfijo utilizando una pila
     * @param expresion una lista de elementos con números u operadores
     * @return el resultado de la evaluación de la expresión.
     */
    static int evaluarPostFija(List<Token> expresion) throws Exception {
        Stack<Integer> pila = new Stack<>();

        if(!expresion.isEmpty()){
            for (int i = 0; i < expresion.size(); i++) {
                Token token = expresion.get(i);
                if(token.isNumber()){
                    pila.push(token.getNumber());
                } else if(token.isOperator()){
                    char operador = token.getOperator();
                    if(pila.size() < 2){
                        throw new Exception("No hay suficientes numeros para ejecutar la operacion " + operador);
                    }
                    else{
                        Integer ultimoElemento = pila.pop();
                        Integer penultimoElemento = pila.pop();
                        Integer resultadoOperacion = 0;
                        switch (operador){
                            case '+':
                                resultadoOperacion = penultimoElemento + ultimoElemento;
                                break;
                            case '-':
                                resultadoOperacion = penultimoElemento - ultimoElemento;
                                break;
                            case '*':
                                resultadoOperacion = penultimoElemento * ultimoElemento;
                                break;
                            case '/':
                                resultadoOperacion = penultimoElemento / ultimoElemento;
                                break;
                            case '%':
                                resultadoOperacion = penultimoElemento % ultimoElemento;
                                break;
                            case '^':
                                resultadoOperacion = penultimoElemento ^ ultimoElemento;
                                break;
                            default:
                                throw new Exception("Operador " + token.getOperator() + " no soportado");
                        }
                        pila.push(resultadoOperacion);
                    }
                }
            }
        }

        if(pila.size() > 1){
            throw new Exception("Hay demasiados elementos en la pila");
        }

        return pila.pop();
    }

    /**
     * Programa principal
     */
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);

        System.out.print("> ");
        String linea = teclado.nextLine();

        try {
            List<Token> expresion = Token.dividir(linea);
            System.out.println(evaluarPostFija(expresion));
        }
        catch (Exception e) {
            System.err.printf("Error grave en la expresión: %s", e.getMessage());
        }

    }
}
