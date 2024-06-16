from sklearn import preprocessing   # type: ignore
import matplotlib.pyplot as plt     # type: ignore
import pandas as pd                 # type: ignore
import numpy as np                  # type: ignore
import csv
pd.options.mode.copy_on_write = True


projects = ["checksum", "digits", "grade", "median", "smallest", "syllables"]
project_colors = {"checksum":"b", "digits":"g", "grade":"r", "median":"c", "smallest":"m", "syllables":"y"}

def main():
    with open('./distance_results.csv','r') as target_file:
        data = pd.read_csv(target_file, delimiter=',')
        process_single(data, "LD Score", "white")
        process_single(data, "LCS Score", "white")
        process_single(data, "SW Score", "white")
        
        process_single(data, "LD Score", "black")
        process_single(data, "LCS Score", "black")
        process_single(data, "SW Score", "black")
        
        # for project in projects:
        #     process_results(data, project, "LD Score")
        #     process_results(data, project, "LCS Score")
        #     process_results(data, project, "SW Score")

def process_results(data, project, sim_measure):
    # get project data
    project_data = data.loc[data["Project"] == project]
    score = project_data[sim_measure]
    project_data[sim_measure] = (score - score.min()) / (score.max() - score.min()) * 10
    project_data.sort_values(sim_measure, inplace=True)

    # fit simple regression lines
    m_bb, b_bb = np.polyfit(project_data[sim_measure], project_data["Black Box Pass %"], 1)
    m_wb, b_wb = np.polyfit(project_data[sim_measure], project_data["White Box Pass %"], 1)
    # plot
    x_lbl = ""
    if (sim_measure == "LD Score"):
        x_lbl = "Levenshtein Distance to Reference - " + project.title() 
    elif (sim_measure == "LCS Score"):
        x_lbl = "Longest Common Subseq with Reference - " + project.title()
    else:
        x_lbl = "Smith-Waterman Score w.r.t Reference - " + project.title()
        
    project_data.plot(x=sim_measure, xlabel=x_lbl, y="Black Box Pass %", ylabel="Black Box Pass %", style=project_colors[project]+"o")
    plt.plot(project_data[sim_measure], (m_bb*project_data[sim_measure]+b_bb))
    # plt.savefig("./result_plots/"+project+"_"+sim_measure+"_BlackBox")
    plt.show()

    project_data.plot(x=sim_measure, xlabel=x_lbl, y="White Box Pass %", ylabel="White Box Pass %", style=project_colors[project]+"o")
    plt.plot(project_data[sim_measure], (m_wb*project_data[sim_measure]+b_wb))
    # plt.savefig("./result_plots/"+project+"_"+sim_measure+"_WhiteBox")
    plt.show()
    
    plt.close()

def process_single(data, sim_measure, test_type):
    t_type = "Black Box Pass %" if test_type == "black" else "White Box Pass %"

    score = data[sim_measure]
    data[sim_measure] = (score - score.min()) / (score.max() - score.min()) * 10
    data.sort_values(sim_measure, inplace=True)


    for project in projects:
        project_data = data.loc[data["Project"] == project]
        plt.plot(project_data[sim_measure], project_data[t_type], project_colors[project]+"o", label=project.title())
        plt.legend(loc="best")

    m, b = np.polyfit(data[sim_measure], data[t_type], 1)
    plt.plot(data[sim_measure], (m*data[sim_measure]+b))
    plt.xlabel(sim_measure)
    plt.ylabel(t_type)
    plt.savefig("./result_plots/all_plots_" + sim_measure + "_" + test_type)

    plt.show()


main()