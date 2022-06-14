#MakeFile para construccion de Server
all : Mainserver

PATH_CHSOCKET=/home/abner/Desktop/TC3/breakOutTec/breakout/server/socketlibrary
CFLAGS = -g -I. -I$(PATH_CHSOCKET)

Server : Mainserver.o
	cc Mainserver.o -L$(PATH_CHSOCKET) -lChSocket -o Mainserver
clean :
	rm *.o Mainserver