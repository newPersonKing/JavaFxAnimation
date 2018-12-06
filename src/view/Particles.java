package view;

import javafx.animation.*;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * Created by emcc on 2018/12/6.
 */
public class Particles extends Pane{

    //烟雾图片
    Image image;
    //x坐标
    DoubleProperty x = new SimpleDoubleProperty(0);
    //y坐标
    DoubleProperty y = new SimpleDoubleProperty(10);
    //粒子半径
    double raidus;
    //水平速度
    double vx;
    //垂直速度
    double vy;
    //加速度
    DoubleProperty acc = new SimpleDoubleProperty();
    //粒子存在时长
    DoubleProperty timer = new SimpleDoubleProperty(100);

    public Particles(){
        image = new Image(getClass().getResourceAsStream("/img/A1.png"));
        create();
//        PathAnimation();
//        ParallelAnimation();
//        SequentialAnimation();
        TimelineEventsAnimation();
    }

    private void create() {
        x.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

            }
        });
        ImageView view = new ImageView(image);
//        /*绑定view的属性到SimpleDoubleProperty*/
        view.xProperty().bind(this.x);
        view.yProperty().bind(this.y);
        /*相同属性不能重复设置 比如这里 设置透明度  下面 在设置 透明动画 就会报错*/
//        view.opacityProperty().bind(this.timer.divide(100));
        getChildren().add(view);
        Timeline timeline = new Timeline();
        KeyValue keyValueStart = new KeyValue(x,100);
        KeyFrame keyFrameStart = new KeyFrame(Duration.millis(1000),keyValueStart);

        timeline.getKeyFrames().addAll(keyFrameStart);
        timeline.play();
        timeline.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Timeline newTimeLine = new Timeline();
                KeyValue keyValueEnd = new KeyValue(y,300);
                KeyFrame keyFrameEnd = new KeyFrame(Duration.millis(1000),keyValueEnd);
                newTimeLine.setCycleCount(Timeline.INDEFINITE);
//                newTimeLine.setAutoReverse(true);
                newTimeLine.getKeyFrames().add(keyFrameEnd);
                newTimeLine.play();
            }
        });
        /*透明动画*/
        FadeTransition ft = new FadeTransition(Duration.millis(3000), view);
        ft.setFromValue(1.0);
        ft.setToValue(0.1);
        ft.setCycleCount(Timeline.INDEFINITE);
        ft.setAutoReverse(true);
        ft.play();
    }

    private void PathAnimation(){
        BorderPane root = new BorderPane();

        //创建一个矩形
        final Rectangle rect=new Rectangle(0, 0, 40, 40);
        rect.setArcHeight(10);
        rect.setArcWidth(10);
        rect.setFill(Color.RED);
        //将矩形作为一个Node方到Parent中
        root.getChildren().add(rect);

        //创建路径
        javafx.scene.shape.Path path=new javafx.scene.shape.Path();
        path.getElements().add(new MoveTo(20, 20));
        path.getElements().add(new CubicCurveTo(380, 0, 380, 120, 200, 120));
        path.getElements().add(new CubicCurveTo(0, 120, 0, 240, 380, 240));
        //创建路径转变
        PathTransition pt=new PathTransition();
        pt.setDuration(Duration.millis(4000));//设置持续时间4秒
        pt.setPath(path);//设置路径
        pt.setNode(rect);//设置物体
        pt.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        //设置周期性，无线循环
        pt.setCycleCount(Timeline.INDEFINITE);
        pt.setAutoReverse(true);//自动往复
        pt.play();//启动动画
        getChildren().add(root);
    }

    /*组合动画*/
    private void ParallelAnimation(){
        BorderPane root = new BorderPane();

        Rectangle rectParallel = new Rectangle(10,20,50, 50);
        rectParallel.setArcHeight(15);
        rectParallel.setArcWidth(15);
        rectParallel.setFill(Color.DARKBLUE);
        rectParallel.setTranslateX(50);
        rectParallel.setTranslateY(75);
        root.getChildren().add(rectParallel);
        //定义矩形的淡入淡出效果
        FadeTransition fadeTransition=new FadeTransition(Duration.millis(3000), rectParallel);
        fadeTransition.setFromValue(1.0f);
        fadeTransition.setToValue(0.3f);
        fadeTransition.setCycleCount(2);
        fadeTransition.setAutoReverse(true);
        //fadeTransition.play();

        //定义矩形的平移效果
        TranslateTransition translateTransition=new TranslateTransition(Duration.millis(2000), rectParallel);
        translateTransition.setFromX(50);
        translateTransition.setToX(350);
        translateTransition.setCycleCount(2);
        translateTransition.setAutoReverse(true);
        //translateTransition.play();

        //定义矩形旋转效果
        RotateTransition rotateTransition =
                new RotateTransition(Duration.millis(3000), rectParallel);
        rotateTransition.setByAngle(180f);//旋转度数
        rotateTransition.setCycleCount(4);
        rotateTransition.setAutoReverse(true);
        //rotateTransition.play();

        //矩形的缩放效果
        ScaleTransition scaleTransition =
                new ScaleTransition(Duration.millis(2000), rectParallel);
        scaleTransition.setToX(2f);
        scaleTransition.setToY(2f);
        scaleTransition.setCycleCount(2);
        scaleTransition.setAutoReverse(true);
        //scaleTransition.play();

        //并行执行动画
        ParallelTransition parallelTransition=new ParallelTransition(fadeTransition,rotateTransition,
                translateTransition,scaleTransition);
        parallelTransition.setCycleCount(Timeline.INDEFINITE);
        parallelTransition.play();

        getChildren().add(root);
    }

    private void SequentialAnimation(){
        BorderPane root = new BorderPane();

        Rectangle rectParallel = new Rectangle(10,20,50, 50);
        rectParallel.setArcHeight(15);
        rectParallel.setArcWidth(15);
        rectParallel.setFill(Color.DARKBLUE);
        rectParallel.setTranslateX(50);
        rectParallel.setTranslateY(75);
        root.getChildren().add(rectParallel);
        //定义矩形的淡入淡出效果
        FadeTransition fadeTransition=new FadeTransition(Duration.millis(3000), rectParallel);
        fadeTransition.setFromValue(1.0f);
        fadeTransition.setToValue(0.3f);
        fadeTransition.setCycleCount(2);
        fadeTransition.setAutoReverse(true);
        //fadeTransition.play();

        //定义矩形的平移效果
        TranslateTransition translateTransition=new TranslateTransition(Duration.millis(2000), rectParallel);
        translateTransition.setFromX(50);
        translateTransition.setToX(350);
        translateTransition.setCycleCount(2);
        translateTransition.setAutoReverse(true);
        //translateTransition.play();

        //定义矩形旋转效果
        RotateTransition rotateTransition =
                new RotateTransition(Duration.millis(3000), rectParallel);
        rotateTransition.setByAngle(180f);//旋转度数
        rotateTransition.setCycleCount(4);
        rotateTransition.setAutoReverse(true);
        //rotateTransition.play();

        //矩形的缩放效果
        ScaleTransition scaleTransition =
                new ScaleTransition(Duration.millis(2000), rectParallel);
        scaleTransition.setToX(2f);
        scaleTransition.setToY(2f);
        scaleTransition.setCycleCount(2);
        scaleTransition.setAutoReverse(true);
        //scaleTransition.play();

        //并行执行动画
        SequentialTransition  sequentialTransition=new SequentialTransition (fadeTransition,rotateTransition,
                translateTransition,scaleTransition);
        sequentialTransition.setCycleCount(Timeline.INDEFINITE);
        sequentialTransition.play();

        getChildren().add(root);
    }

    /*Timeline Events*/
    private void TimelineEventsAnimation(){
        Timeline timeline;
        AnimationTimer timer;
        Group p = new Group();
        p.setTranslateX(80);
        p.setTranslateY(80);

        //create a circle with effect
        final Circle circle = new Circle(20,  Color.rgb(156,216,255));
        circle.setEffect(new Lighting());
        //create a text inside a circle
        final Text text = new Text ("GOGOING");
        text.setStroke(Color.BLACK);
        //create a layout for circle with text inside
        final StackPane stack = new StackPane();
        stack.getChildren().addAll(circle, text);
        stack.setLayoutX(30);
        stack.setLayoutY(30);

        p.getChildren().add(stack);

        //create a timeline for moving the circle
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);

//You can add a specific action when each frame is started. timer类似一个计时器 只要启动 就会 一直 执行
        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                System.out.println("AnimationTimer"+l);
                text.setText(l+"");
            }
        };

        //create a keyValue with factory: scaling the circle 2times
        KeyValue keyValueX = new KeyValue(stack.scaleXProperty(), 2);
        KeyValue keyValueY = new KeyValue(stack.scaleYProperty(), 2);

        //create a keyFrame, the keyValue is reached at time 2s
        Duration duration = Duration.millis(2000);
        //one can add a specific action when the keyframe is reached
        EventHandler onFinished = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                stack.setTranslateX(java.lang.Math.random()*200-100);
                //reset counter

            }
        };

        KeyFrame keyFrame = new KeyFrame(duration, onFinished , keyValueX, keyValueY);

        //add the keyframe to the timeline
        timeline.getKeyFrames().add(keyFrame);

        timeline.play();
        timer.start();

         getChildren().add(p);
    }


}
