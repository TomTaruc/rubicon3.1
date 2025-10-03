package com.gabriel.drawfx.command;

import java.util.Stack;

public class CommandService {
    private static final Stack<Command> undoStack = new Stack<>();
    private static final Stack<Command> redoStack = new Stack<>();

    public static void ExecuteCommand(Command command) {
        command.execute();
        undoStack.push(command);
        redoStack.clear(); // Clear redo stack when new command is executed
    }

    public static void RecordCommand(Command command) {
        undoStack.push(command);
        redoStack.clear(); // Clear redo stack when new command is recorded
    }

    public static boolean canUndo() {
        return !undoStack.empty();
    }

    public static boolean canRedo() {
        return !redoStack.empty();
    }

    public static void undo() {
        if (undoStack.empty())
            return;
        Command command = undoStack.pop();
        command.undo();
        redoStack.push(command);
    }

    public static void redo() {
        if (redoStack.empty())
            return;
        Command command = redoStack.pop();
        command.execute();
        undoStack.push(command);
    }
    
    public static void clear() {
        undoStack.clear();
        redoStack.clear();
    }
    
    public static int getUndoStackSize() {
        return undoStack.size();
    }
    
    public static int getRedoStackSize() {
        return redoStack.size();
    }
}