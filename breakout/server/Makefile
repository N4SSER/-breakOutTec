#MakeFile para construccion de Server
all : S

PATH_CHSOCKET=/home/abner/Desktop/TC3/breakOutTec/breakout/server/socketlibrary
CFLAGS = -g -I. -I$(PATH_CHSOCKET)

S : Mainserver.o
	cc Mainserver.o -lpthread -L$(PATH_CHSOCKET) -lChSocket -o Mainserver
clean :
	rm *.o Mainserver