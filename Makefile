# java compiler and compiler flags
JAVAC = javac
JAVAC_FLAGS = -cp ./src --module-path "./javafx-sdk-linux/lib" --add-modules=javafx.controls,javafx.fxml

# java execution command and flags
JAVA = java
JAVA_FLAGS = -cp ./src --module-path "./javafx-sdk-linux/lib" --add-modules=javafx.controls,javafx.fxml

SRC_DIR = src

MAIN_CLASS = Main

all: compile run

compile:
	$(JAVAC) $(JAVAC_FLAGS) $(SRC_DIR)/*.java

run:
	$(JAVA) $(JAVA_FLAGS) $(MAIN_CLASS)

clean:
	rm -rf $(SRC_DIR)/*.class

.PHONY: all compile run clean
