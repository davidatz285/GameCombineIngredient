package com.example.gamecombineingredient

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.view.MotionEvent
import android.view.View
import android.view.ViewTreeObserver
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.contains
import kotlinx.android.synthetic.main.activity_game.view.*
import java.util.*

class GameView(context: Context) : LinearLayout(context), IMainActivity, View.OnClickListener, View.OnTouchListener {

    lateinit var canvas: Canvas
    val presenter: Presenter
    val paint: Paint
    var imageView: ImageView
    lateinit var bitmap: Bitmap
    var btnAddMeat: Button
    var btnAddBun: Button
    var btnAddSausage: Button
    var linearLayout: LinearLayout
    var positionBun: LinkedList<Position> //default starting position for bun
    var positionMeat: LinkedList<Position> //default starting position for meat
    var positionSausage: LinkedList<Position>
    var positionBunFilled: BooleanArray
    var positionMeatFilled: BooleanArray
    var positionSausageFilled: BooleanArray

    init {
        View.inflate(context, R.layout.activity_game, this)
        this.presenter = Presenter(this)
        this.paint = Paint()
        paint.isAntiAlias = true
        this.positionBun = LinkedList()
        this.positionMeat = LinkedList()
        this.positionSausage = LinkedList()
        this.positionBunFilled = booleanArrayOf(false, false, false)
        this.positionMeatFilled = booleanArrayOf(false, false, false)
        this.positionSausageFilled = booleanArrayOf(false, false, false)
        this.btnAddMeat = findViewById(R.id.btn_add_meat)
        this.btnAddBun = findViewById(R.id.btn_add_bun)
        this.btnAddSausage = findViewById(R.id.btn_add_sausage)
        this.imageView = findViewById(R.id.ivCanvas)
        this.imageView.setOnTouchListener(this)
        this.linearLayout = findViewById(R.id.root)
        initiateCanvas()
        this.btnAddBun.setOnClickListener(this)
        this.btnAddMeat.setOnClickListener(this)
        this.btnAddSausage.setOnClickListener(this)
        positionSausage.add(Position(imageView.width / 5, imageView.height / 3 + 600, imageView.width / 5 + 300, imageView.height / 3 + 800))
        positionSausage.add(Position(imageView.width / 5, imageView.height / 3 + 900, imageView.width / 5 + 300, imageView.height / 3 + 1100))
        positionSausage.add(Position(imageView.width / 5, imageView.height / 3 + 1200, 2 * imageView.width / 5 + 300, imageView.height / 3 + 1400))
        positionBun.add(Position(imageView.width / 5 + 400, imageView.height / 3 + 600, imageView.width / 5 + 600, imageView.height / 3 + 800))
        positionBun.add(Position(imageView.width / 5 + 400, imageView.height / 3 + 900, imageView.width / 5 + 600, imageView.height / 3 + 1100))
        positionBun.add(Position(imageView.width / 5 + 400, imageView.height / 3 + 1200, 2 * imageView.width / 5 + 600, imageView.height / 3 + 1400))
        positionMeat.add(Position(imageView.width / 5 + 800, imageView.height / 3 + 600, imageView.width / 5 + 1000, imageView.height / 3 + 800))
        positionMeat.add(Position(imageView.width / 5 + 800, imageView.height / 3 + 900, imageView.width / 5 + 1000, imageView.height / 3 + 1100))
        positionMeat.add(Position(imageView.width / 5 + 800, imageView.height / 3 + 1200, 2 * imageView.width / 5 + 1000, imageView.height / 3 + 1400))
    }


    fun initiateCanvas() {
        var vto: ViewTreeObserver = linearLayout.viewTreeObserver
        vto.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                linearLayout.viewTreeObserver.removeOnGlobalLayoutListener(this)
                var width: Int = linearLayout.measuredWidth
                var height: Int = linearLayout.measuredHeight
                bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
                imageView.setImageBitmap(bitmap)
                canvas = Canvas(bitmap)
                canvas.drawColor(Color.WHITE)
            }
        })
        imageView.invalidate()
    }

    override fun onClick(view: View?) {
        var counterBun : Int = presenter.arrBun.size
        var counterMeat : Int = presenter.arrMeat.size
        var counterSausage : Int = presenter.arrSausage.size
        if (view?.id == this.btnAddBun.id) {
            var bun: Bun = Bun(positionBun.get(counterBun%3).left, positionBun.get(counterBun%3).top, positionBun.get(counterBun%3).right, positionBun.get(counterBun%3).bottom)
            this.addBun(bun)
            var bitmap: Bitmap = BitmapFactory.decodeResource(resources,R.drawable.bun)
            canvas.drawBitmap(bitmap,null,presenter.arrBun.get(counterBun%3).rect,null)
        } else if (view?.id == this.btnAddMeat.id) {
            var meat: Meat = Meat(positionMeat.get(counterMeat%3).left, positionMeat.get(counterMeat%3).top, positionMeat.get(counterMeat%3).right, positionMeat.get(counterMeat%3).bottom)
            this.addMeat(meat)
            var bitmap: Bitmap = BitmapFactory.decodeResource(resources,R.drawable.meat)
            canvas.drawBitmap(bitmap,null,presenter.arrMeat.get(counterMeat%3).rect,null)
        } else if (view?.id == this.btnAddSausage.id) {
            var sausage: Sausage = Sausage(positionSausage.get(counterSausage%3).left, positionSausage.get(counterSausage%3).top, positionSausage.get(counterSausage%3).right, positionSausage.get(counterSausage%3).bottom)
            this.addSausage(sausage)
            var bitmap: Bitmap = BitmapFactory.decodeResource(resources,R.drawable.sausage)
            canvas.drawBitmap(bitmap,null,presenter.arrSausage.get(counterSausage%3).rect,null)
        }
        imageView.invalidate()
    }

    override fun onTouch(view: View?, event: MotionEvent?): Boolean {
        var active: Food? = Food(0,0,0,0)

        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                active = null
                for (item in presenter.arrBun) {
                    if (item.rect.contains(event?.x.toInt(), event?.y.toInt())) {
                        active = item
                        break
                        return true
                    }
                }
                for (item in presenter.arrMeat) {
                    if (item.rect.contains(event?.x.toInt(), event?.y.toInt())) {
                        active = item
                        break
                        return true
                    }
                }
                for (item in presenter.arrSausage) {
                    if (item.rect.contains(event?.x.toInt(), event?.y.toInt())) {
                        active = item
                        break
                        return true
                    }
                }
                for (item in presenter.arrHotdog) {
                    if (item.rect.contains(event?.x.toInt(), event?.y.toInt())) {
                        active = item
                        break
                        return true
                    }
                }
                for (item in presenter.arrBurger) {
                    if (item.rect.contains(event?.x.toInt(), event?.y.toInt())) {
                        active = item
                        break
                        return true
                    }
                }

            }
            MotionEvent.ACTION_MOVE -> {
                if(active!=null){
                    active.rect.set(event.x.toInt()-100,event.y.toInt()-100,event.x.toInt()+100,event.y.toInt()+100)
                }

                /*if (active.jenis.equals("bun")) {

                }*/

            }
            MotionEvent.ACTION_UP -> {

                invalidate()
            }
        }
        invalidate()
        return true
    }
    override fun draw(canvas:Canvas?){
        super.draw(canvas)
        var bitmap : Bitmap = BitmapFactory.decodeResource(resources,R.drawable.bun)
        for(items in presenter.arrBun){
            canvas?.drawBitmap(bitmap,items.rect,items.rect,null)
        }
    }
    override fun clearAll() {
        presenter.clear()
    }

    override fun addBun(bun: Bun) {
        presenter.addBun(bun)
    }

    override fun addBurger(burger: Burger) {
        presenter.addBurger(burger)
    }

    override fun addMeat(meat: Meat) {
        presenter.addMeat(meat)
    }

    override fun addCustomer(customer: Customer) {
        presenter.addCustomer(customer)
    }

    override fun addSausage(sausage: Sausage) {
        presenter.addSausage(sausage)
    }

    override fun addHotdog(hotdog: Hotdog) {
        presenter.addHotdog(hotdog)
    }


}