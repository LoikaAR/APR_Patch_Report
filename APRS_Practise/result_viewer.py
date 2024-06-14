from sklearn import preprocessing   # type: ignore
import matplotlib.pyplot as plt     # type: ignore
import pandas as pd                 # type: ignore
import numpy as np                  # type: ignore
import csv
pd.options.mode.copy_on_write = True


projects = ["checksum", "digits", "grade", "median", "smallest", "syllables"]

with open('./distance_results.csv','r') as target_file:
    data = pd.read_csv(target_file, delimiter=',')
    for project in projects:
        # get project data
        project_data = data.loc[data["Project"] == project]

        # normalize and sort scores
        LD_score = project_data["LD Score"]
        project_data["LD Score"] = (LD_score - LD_score.min()) / (LD_score.max() - LD_score.min()) * 10 # normalized LD
        project_data.sort_values("LD Score", inplace=True)
        
        # fit simple regression line
        m, b = np.polyfit(project_data["LD Score"], project_data["Black Box Pass %"], 1)
        
        # plot
        project_data.plot(x="LD Score", xlabel="Levenshtein Distance to Reference", y="Black Box Pass %", ylabel="Black Box Pass %", style="o")
        plt.plot(project_data["LD Score"], (m*project_data["LD Score"]+b))
        plt.show()
