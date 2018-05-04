# Server Program for HW1 Q4

import socket 
import sys 

# Create a TCP server socket
#     (AF_INET is used for IPv4 protocols)
#     (SOCK_STREAM is used for TCP)
try:
    serverSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    print("Socket successfully created")
except socket.error as err:
    print("socket creation failed with error %s" %(err))

# Assign a port number
port = 12345

# Bind the socket to server address and server port
try:
    host_ip = socket.gethostbyname('localhost')
except socket.gaierror: # getaddrinfo
    print("there was an error resolving the host")
    sys.exit()
    
serverSocket.bind((host_ip,port))

# Listen to at most 1 connection at a time
serverSocket.listen(1)

# Server should be up and running and listening to the incoming connections
while True:
    print('The server is ready to receive')

    # Set up a new connection from the client
    connectionSocket,addr = serverSocket.accept()

    #try:
    # Receives the request message from the client
    message = connectionSocket.recv(1024).decode()
    print(message)

    # Extract the path of the requested object from the message
    #     The path is the second part of HTTP header, identified by [1]
    filename = message.split()[1]
    print(filename)

    # Because the extracted path of the HTTP request includes 
    #     a character '\', we read the path from the second character 
    f = open(filename[1:],'r')

    # Store the entire content of the requested file in a temporary buffer
    outputdata = f.read()

    # Send the HTTP response header line to the connection socket
    connectionSocket.send("HTTP/1.1 200 OK\r\n\r\n".encode()) 

    # Send the content of the requested file to the connection socket
    for i in range(0, len(outputdata)):  
        connectionSocket.send(outputdata[i].encode())
    connectionSocket.send("\r\n".encode()) 
    #except:
     #   print("Unexpected error occured")

    # Close the client connection socket
    connectionSocket.close()


serverSocket.close()  
sys.exit()

# Terminate the program after sending the corresponding data