#CSE310 with Martin Radfar
#Ivan Lin

# server program
# Import socket module
from socket import *
import sys # In order to terminate the program

# Create a TCP server socket
#(AF_INET is used for IPv4 protocols)
#(SOCK_STREAM is used for TCP)

serverSocket = socket(AF_INET, SOCK_STREAM)

# Assign a port number
port=8001

# Bind the socket to server address and server port
serverSocket.bind(('',port))
print("\n\nListening on port "+str(port))

# Listen to at most 1 connection at a time
serverSocket.listen(1)


# Server should be up and running and listening to the incoming connections

while True:
    print('\n\nThe server is ready to receive')

    # Set up a new connection from the client
    connectionSocket, address = serverSocket.accept()
    print("\n\nReceived request from "+str(address))
        # Receives the request message from the client
    try:

        message = connectionSocket.recv(1024).decode()
        print("Printing raw request-----\n"+str(message))
        # Extract the path of the requested object from the message
        # The path is the second part of HTTP header, identified by [1]
        filename = message.split()[1]
        # Because the extracted path of the HTTP request includes
        # a character '\', we read the path from the second character
        f=open(filename[1:],"r")
        # Store the entire contenet of the requested file in a temporary buffer
        outputdata = f.read()
        # Send the HTTP response header line to the connection socket
        connectionSocket.send("HTTP/1.1 200 OK\r\n\r\n".encode())

        # Send the content of the requested file to the connection socket
        for i in range(0, len(outputdata)):
            connectionSocket.send(outputdata[i].encode())
        connectionSocket.send("\r\n".encode())
        f.close()
    except:
        print("Problem with client request. Closing connection.")
        connectionSocket.send("HTTP/1.1 404 Not Found\r\n\r\n".encode())
    # Close the client connection socket
    connectionSocket.close()
    # Close the file


serverSocket.close()
sys.exit()#Terminate the program after sending the corresponding data