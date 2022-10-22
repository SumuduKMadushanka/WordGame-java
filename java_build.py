import json
import subprocess
import os
from colorama import Fore

config_file = open("configs.json")
CONFIGS = json.load(config_file)
config_file.close()

CWD = os.getcwd()
PROJECT = CONFIGS["project"]
BIN_LOCATION = CONFIGS["bin_location"]
SRC_LOCATION = CONFIGS["src_location"]
FILE_LIST = CONFIGS["file_list"]

def print_info(text):
    print ("| INFO | " + Fore.GREEN + text + Fore.RESET)

def print_error(text):
    print ("| ERROR | " + Fore.RED + text + Fore.RESET)

def compile_java_file(java_file):
    print_info ("Compiling {} file".format(java_file))
    command = "javac {} -d {}".format(java_file, BIN_LOCATION)
    c_process = subprocess.Popen(command, shell=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
    c_process.wait()
    if (c_process.returncode != 0):
        for line in (c_process.stderr):
            print_error (line.decode('UTF-8')[:-1])
        return -1
    else:
        print_info ("Compiling {} Completed".format(java_file))
        return 0

def compile(file_name):
    java_file = file_name + ".java"
    class_file = file_name + ".class"

    if (os.path.exists(BIN_LOCATION + "\\" + class_file)):
        modified_time_java_file = os.path.getmtime(java_file)
        modified_time_class_file = os.path.getmtime(BIN_LOCATION + "\\" + class_file)

        if (modified_time_class_file > modified_time_java_file):
            return 2

    return compile_java_file(java_file)

# Main Code
has_compile_errors = False
print_info("-------- Start Compiling {} --------".format(PROJECT))

if not (os.path.exists(BIN_LOCATION)):
    os.mkdir(BIN_LOCATION)
    print_info("Bin dir created")

os.chdir("{}\\{}".format(CWD, SRC_LOCATION))
if (CWD != os.getcwd()):
    for it in range(len(os.getcwd().split(CWD)[1].split("\\")) - 1):
        BIN_LOCATION = "..\\{}".format(BIN_LOCATION)

for file_name in FILE_LIST:
    compile_code = compile(file_name)
    if (compile_code == 2):
        print_info ("{}.java was already Compiled".format(file_name))
    elif (compile_code == -1):
        print_error("-------- Compilation Failed --------")
        has_compile_errors = True
        break

if not has_compile_errors:
        print_info("-------- Complete the {} Compilation Successfully --------".format(PROJECT))

os.chdir(CWD)