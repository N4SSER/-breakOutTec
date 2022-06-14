#include <Socket_Servidor.h>
#include <Socket.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <pthread.h>
#include <string.h>
#include <netinet/in.h>

struct player_msg_info{
            int socketcliente;
            char *datos;
            int longitud_datos;
            int *flag;
        };

/*
Checks incoming messages from server and prints them on screen
*/
/*void * checkMessages(int socketcliente, char *Datos, int longitud, int *flag){
    while (*flag>0){
        if(Lee_Socket(socketcliente, Datos, longitud)>0)
            printf("Recibido: %s", Datos);     
    }
}*/

void *checkMessages(void *input){
    while (((struct player_msg_info*)input)->flag>0){
        if(Lee_Socket(((struct player_msg_info*)input)->socketcliente, ((struct player_msg_info*)input)->datos, ((struct player_msg_info*)input)->longitud_datos)>0)
            printf("Recibido: %s", ((struct player_msg_info*)input)->datos);     
    }
}

int main()
{
    int Socket_Servidor;
    int Socket_Cliente;
    
    int Socket;

    int Aux;
    int Longitud_Cadena;
    int flag_on;
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
    if (Socket_Cliente == -1){
        printf("No se puede abrir socket de cliente\n");
        exit(-1);
    } else {
        //flag changed to enable message checking
        flag_on = 1;
        //creation of struct with chechMessages function's parameters
        struct player_msg_info *CMessages = (struct player_msg_info * )malloc(sizeof(struct player_msg_info));
        CMessages->socketcliente=Socket_Cliente;
        CMessages->datos=Cadena;
        CMessages->longitud_datos=Longitud_Cadena;
        CMessages->flag=&flag_on;

        //thread for checking messages in the background
        pthread_t listen_to_messages;
        pthread_create(&listen_to_messages, NULL, checkMessages, (void *)CMessages);
        pthread_join(listen_to_messages, NULL);

    }

    /*
    *Se envia un entero con la longitud de una cadena (incluido el caracter nulod del final)
    *Lueg se envia la cadena
    */

    /*Longitud_Cadena = 5;
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
    printf("Servidor C: Recibido %s\n",Cadena);*/


    /*
    LISTA DE COMANDOS PARA MODIFICAR LADRILLOS

    ** ASIGNAR/CAMBIAR PUNTUACION A NIVEL:              1 <1-3> s
    ** ASIGNAR VIDAS A LADRILLO ESPECIFICO:             2 <i> <j>
    ** ASIGNAR BOLA A LADRILLO ESPECIFICO:              3 <i> <j>
    ** ASIGNAR RAQUETA DOBLE A LADRILLO ESPECIFICO:     4 <i> <j>
    ** ASIGNAR RAQUETA MITAS LADRILLO ESPECIFICO:       5 <i> <j>
    ** ASIGNAR VELOCIDAD MAS A LADRILLO ESPECIFICO:     6 <i> <j>
    ** ASIGNAR VELOCIDAD MENOS A LADRILLO ESPECIFICO:   7 <i> <j>
    */
    while(flag_on>0){
        char message[5];
        scanf("%s",message);
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
        printf("%s\n", message);

    }

    close(Socket_Cliente);
    close(Socket_Servidor);

    return 0;

}