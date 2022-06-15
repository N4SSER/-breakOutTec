#include <Socket_Servidor.h>
#include <Socket.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <pthread.h>
#include <string.h>
#include <netinet/in.h>

/**
 * @brief struct with players info for checking its messages
 * 
 */
struct player_msg_info{
            int socketcliente;
            char *datos;
            int longitud_datos;
            int *flag;
};


/*
Mensajes para observador
puntos

*/
void *checkMessages(void *input){
    struct player_msg_info *args = input;
    while (*(args->flag)>0){
        if(Lee_Socket(args->socketcliente, args->datos, args->longitud_datos)>0)
            printf("Recibido: %s", args->datos);     
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
    char message[15];
    pthread_t listen_to_messages;
    
    /**
     * @brief opens socket from cpp_java service in /etc/services wich is translated to port 35557
     * 
     */
    Socket_Servidor = Abre_Socket_Inet("cpp_java");
    if (Socket_Servidor == -1){
        printf ("No se puede abrir socket del servidor\n");
        exit (-1);
    } else {
        printf ("Socket abierto exitosamente\n");
    }
    
    /**
     * @brief waits for clients
     * 
     */
    printf("Esperando Clientes...\n");
    Socket_Cliente = Acepta_Conexion_Cliente (Socket_Servidor);
    
    /**
     * @brief checks if client connection was succesful
     * 
     */
    if (Socket_Cliente == -1){
        printf("No se puede abrir socket del cliente\n");
        exit(-1);
    
    } else {
        
        //flag changed to enable message checking
        flag_on = 1;
        printf("Cliente conectado exitosamente\n");
        //creation of struct with chechMessages function's parameters
        struct player_msg_info CMessages;
        CMessages.socketcliente=Socket_Cliente;
        CMessages.datos=Cadena;
        CMessages.longitud_datos=6;
        CMessages.flag=&flag_on;
        printf("struct created\n");
        //thread for checking messages in the background
        printf("thread declared\n");
        pthread_create(&listen_to_messages, NULL, checkMessages, (void *)&CMessages);
        printf("thread created\n");
        //pthread_join(listen_to_messages, NULL);
        printf("thread joined\n");

    }


    
    char commands1[] = "LISTA DE COMANDOS PARA MODIFICAR LADRILLOS\n";
    char commands2[]="two digits minimum per argument\n";
    char commands3[]="** ASIGNAR/CAMBIAR PUNTUACION A NIVEL:              1 <1-3> <puntos>\n";
    char commands4[]="** ASIGNAR VIDAS A LADRILLO ESPECIFICO:             2 <i> <j>\n";
    char commands5[]="** ASIGNAR BOLA A LADRILLO ESPECIFICO:              3 <i> <j>\n";
    char commands6[]="** ASIGNAR RAQUETA DOBLE A LADRILLO ESPECIFICO:     4 <i> <j>\n";
    char commands7[]="** ASIGNAR RAQUETA MITAS LADRILLO ESPECIFICO:       5 <i> <j>\n";
    char commands8[]="** ASIGNAR VELOCIDAD MAS A LADRILLO ESPECIFICO:     6 <i> <j>\n";
    char commands9[]="** ASIGNAR VELOCIDAD MENOS A LADRILLO ESPECIFICO:   7 <i> <j>\n";
    

    while(flag_on>0){
        printf(commands1);
        printf(commands2);
        printf(commands3);
        printf(commands4);
        printf(commands5);
        printf(commands6);
        printf(commands7);
        printf(commands8);
        printf(commands9);
        printf("Esperando comandos...\n");
        scanf("%s",message);

        Longitud_Cadena = strlen(message);
        
        if(strcmp(message, "exit")==0){
            break;
        }
        //El entero se transforma a formato red
        Aux = htonl (Longitud_Cadena);

        //Se envia el entero transformado
        Escribe_Socket(Socket_Cliente, (char *)&Aux, sizeof(Longitud_Cadena));
        
        //Se envia la cadena
        Escribe_Socket(Socket_Cliente, message, Longitud_Cadena);
        //printf("%s\n", message);

    }

    close(Socket_Cliente);
    close(Socket_Servidor);

    return 0;

}