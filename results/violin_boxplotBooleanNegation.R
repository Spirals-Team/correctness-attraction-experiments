pdf("violinboxplot_BooleanNegation.pdf")
mar.default <- c(5,4,4,2) + 0.1
par(mar = mar.default + c(0, 4, 0, 0))
names <- c("linreg","laguerre","lcs","canny","rc4","rsa","md5","sudoku","zip","quicksort")
linreg <- c(0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,100.0,100.0,100.0,100.0)
laguerre <- c(0.0,0.0,0.0,0.0,0.0,0.0,0.0,23.85,30.77,32.25,39.13,43.48,46.45,53.54,72.0,77.87,85.17,86.96,90.85,90.85,93.89,96.15,97.16,99.47,99.73)
lcs <- c(0.0,0.58,0.6,2.91,9.05,24.51,44.36,84.11)
canny <- c(0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,1.18,3.75,4.0,8.0,8.48,9.65,10.38,10.59,10.59,11.9,12.5,15.2,15.82,17.86,19.62,20.1,25.0,27.5,32.67,35.86,36.64,42.21,43.64,46.2,49.81,52.63,83.55,88.13,88.93,93.15,93.94,94.39,96.55,99.46,99.49,99.49,100.0,100.0,100.0,100.0,100.0,100.0,100.0,100.0,100.0,100.0,100.0,100.0,100.0,100.0)
rc4 <- c(0.0,0.0,0.0,0.0,0.0,6.23,9.34)
rsa <- c(0.0,0.0,0.0,0.0,0.0,0.0,0.0,50.0,50.0,50.0,50.0,70.0,75.0,100.0,100.0,100.0,100.0,100.0)
md5 <- c(0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,66.67)
sudoku <- c(0.0,0.0,0.0,0.73,3.88,7.91,8.5,9.99,10.0,10.48,10.5,25.84,25.84,25.84,27.74,36.7,75.06,81.39,81.39,86.06,98.06,99.27,99.27,99.27,99.67,100.0)
zip <- c(0.0,0.0,0.0,1.09,1.09)
quicksort <- c(15.65,21.03,61.22,62.08,86.36,99.9)
par(yaxt="n")
vioplot(linreg,laguerre,lcs,canny,rc4,rsa,md5,sudoku,zip,quicksort,names=names, col="lightblue", horizontal=TRUE, drawRect=FALSE, h=5)
points(0.0,1, col="white", bg="white", type="p", pch = 21)
points(46.45,2, col="white", bg="white", type="p", pch = 21)
points(9.05,3, col="white", bg="white", type="p", pch = 21)
points(35.86,4, col="white", bg="white", type="p", pch = 21)
points(0.0,5, col="white", bg="white", type="p", pch = 21)
points(50.0,6, col="white", bg="white", type="p", pch = 21)
points(0.0,7, col="white", bg="white", type="p", pch = 21)
points(25.84,8, col="white", bg="white", type="p", pch = 21)
points(0.0,9, col="white", bg="white", type="p", pch = 21)
points(62.08,10, col="white", bg="white", type="p", pch = 21)
axis(2, at=seq(1, 10, by=1), labels = FALSE)
text(y = seq(1, 10, by=1), par("usr")[1], labels = names, srt = 0, pos = 2, xpd = TRUE)
title(ylab="", xlab="Correctness ratio")
dev.off()
