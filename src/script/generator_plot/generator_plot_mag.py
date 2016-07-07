import generator_plot

subjects=("quicksort", "zip", "sudoku", "md5", "rsa", "rc4", "canny", "lcs", "laguerre", "regression" )
for subject in subjects:
    generator_plot.plot_increasingPerturbation_percentageSuccess("results/"+subject, "IntegerAddM_CallExplorer_analysis_graph_data.txt", "IntegerMagnitudeSys" ,subject, 2, 3, 1)
    f=open("results/IntegerMagnitudeEffect.md", 'w')
    f.write("!["+subject+"Mag]("+subject+"/img/IntegerMagnitudeSys_plot.jpeg\n")
    f.close()
