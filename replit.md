# Drawing Application

## Overview
This is a Java Swing-based drawing application that allows users to create shapes (lines, rectangles, ellipses) with intuitive mouse interactions. The application features automatic selection, visual transformation handles, move/scale operations, and undo/redo functionality.

## Recent Changes (October 2, 2025)

### Replit Environment Setup & Refinements
1. **VNC Workflow**: Configured workflow to build and run the GUI application in VNC
2. **Menu Cleanup**: Removed "Clear All" menu item from Edit menu as requested
3. **Build Command**: Updated to `mvn clean install -q && cd draw && mvn exec:java -Dexec.mainClass="com.gabriel.draw.Main"`

### Enhanced Selection & Transformation Features
1. **Automatic Selection**: Click any shape to automatically select it and display visual handles
2. **Visual Handles**: Selected shapes display transformation handles:
   - Lines: 2 handles at endpoints
   - Rectangles/Ellipses: 8 handles at corners and midpoints
3. **Intelligent Mode Detection**:
   - Click on a handle → automatic SCALE mode
   - Click on shape body → automatic MOVE mode
   - Click empty space → deselect and draw new shapes
4. **Click/Drag Separation**: Selection persists on simple clicks; transformations only activate during actual drag operations
5. **Simplified Menu Bar**: Streamlined to File/Edit/Draw/Properties structure

### Initial Setup
1. **Java Environment**: Installed Java (GraalVM) for project
2. **Maven Build**: Fixed compilation issues by adding missing CLEAR_ALL constant to ActionCommand
3. **Dependencies**: Removed duplicate dependency in draw/pom.xml
4. **Build System**: Successfully built all modules (rubicon, drawfx, draw)
5. **Workflow Configuration**: Configured VNC workflow to run the GUI application
6. **Git Ignore**: Verified comprehensive .gitignore for Java/Maven projects

### Technical Implementation Details
- Added selection state tracking to Shape class with `isSelected()` and `isLine()` methods
- Implemented deferred mode switching: EditMode (MOVE/SCALE) is only set during mouseDragged, not mousePressed
- SearchService detects proximity to handles vs shape body to determine transformation intent
- mousePressed records hit-testing data and delegates selection to AppService
- mouseReleased preserves selection when no drag occurred
- Handle rendering: 6x6 pixel squares at anchor points, drawn in black with white fill

## Project Architecture
- **Root Module**: `rubicon` - Multi-module Maven project
- **Core Module**: `drawfx` - Contains shared interfaces and command pattern implementation
- **GUI Module**: `draw` - Contains Swing UI components and application logic

### Key Components
- `CommandService`: Manages undo/redo stacks using command pattern
- `ActionController`: Handles UI actions and maintains button states
- `DrawingAppService`: Core application service for drawing operations
- `DeawingCommandAppService`: Command wrapper service with undo/redo capabilities
- `DrawingView`: Main drawing canvas with real-time preview
- `DrawingController`: Mouse event handler with automatic mode detection
- `DrawingToolBar`: Toolbar with shape tools and actions
- `DrawingMenuBar`: Simplified menu bar (File/Edit/Draw/Properties)
- Renderer Services: `LineRendererService`, `RectangleRendererService`, `EllipseRenderer` - Draw shapes and selection handles

## User Interactions
- **Create Shape**: Click and drag on empty canvas
- **Select Shape**: Click on any shape to select it and display handles
- **Move Shape**: Click and drag the body of a selected shape
- **Scale Shape**: Click and drag any handle of a selected shape
- **Deselect**: Click on empty canvas
- **Quick Shape**: Shift+Click to create default-sized shape at cursor
- **Undo/Redo**: Use Edit menu or toolbar buttons

## Build & Run
- **Java Version**: Java 17 (GraalVM)
- **Build System**: Maven multi-module project
- **Run Command**: `cd draw && mvn exec:java -Dexec.mainClass="com.gabriel.draw.Main"`
- **Output**: VNC application for GUI display in Replit environment

## Current Status
✅ Application successfully enhanced with selection and transformation features
✅ All transformation workflows tested and verified
✅ Automatic mode detection working correctly
✅ Visual handles rendering properly for all shape types
✅ Click/drag separation prevents spurious shapes and preserves selection
✅ Menu bar simplified to File/Edit/Draw/Properties structure
✅ Ready to use in Replit environment
