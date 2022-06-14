#include <Socket_Servidor.h>
#include <Socket.h>
#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <netinet/in.h>

int main()
{
    int Socket_Servidor;
    int Socket_Cliente;
    
    int Socket;

    int Aux;
    int Longitud_Cadena;

    char Cadena[100];

    
    //Abre el socket server
    Socket_Servidor = Abre_Socket_Inet("cpp_java");
    if (Socket_Servidor == -1){
        printf ("No se puede abrir servidor de socket\n");
        exit (-1);
    } else {
        printf ("Socket abierto exitosamente\n");
    }
    

    //Espera cliente
    Socket_Cliente = Acepta_Conexion_Cliente (Socket_Servidor);
    if (Socket_Servidor == -1){
        printf("No se puede abrir socket de cliente\n");
        exit(-1);
    }

    /*
    *Se envia un entero con la longitud de una cadena (incluido el caracter nulod del final)
    *Lueg se envia la cadena
    */

    Longitud_Cadena = 5;
    strcpy(Cadena, "Hola");

    //El entero se transforma a formato red
    Aux = htonl (Longitud_Cadena);

    //Se envia el entero transformado
    Escribe_Socket(Socket_Cliente, (char *)&Aux, sizeof(Longitud_Cadena));
    printf("Servidor C: Enviado %d\n", Longitud_Cadena-1);

    //Se envia la cadena
    Escribe_Socket(Socket_Cliente, Cadena, Longitud_Cadena);
    printf("Servidor C: Enviado %s\n", Cadena);

    //Se lee la informacion del cliente
    Lee_Socket (Socket_Cliente, (char *)&Aux, sizeof(Longitud_Cadena));

    Longitud_Cadena=ntohl(Aux);
    printf("Servidor C: Recibido %d\n", Longitud_Cadena-1);

    //Se lee la cadena
    Lee_Socket(Socket_Cliente, Cadena, Longitud_Cadena);
    printf("Servidor C: Recibido %s\n",Cadena);

    close(Socket_Cliente);
    close(Socket_Servidor);

    return 0;

}