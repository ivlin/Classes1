function plotData(X, y)
%PLOTDATA Plots the data points X and y into a new figure 
%   PLOTDATA(x,y) plots the data points with + for the positive examples
%   and o for the negative examples. X is assumed to be a Mx2 matrix.

% Create New Figure
figure; hold on;

% ====================== YOUR CODE HERE ======================
% Instructions: Plot the positive and negative examples on a
%               2D plot, using the option 'k+' for the positive
%               examples and 'ko' for the negative examples.
%



for iter = 1: rows(y)
	     if (y(iter:1) >= 0)
	       plot(X(iter:1),X(iter:2),'k+','MarkerSize',10)
	     else
	       plot(X(iter:1),X(iter:2),'ko','MarkerSize',10)


% =========================================================================



hold off;

end
