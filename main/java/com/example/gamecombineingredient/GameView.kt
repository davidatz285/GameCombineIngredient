package com.example.gamecombineingredient

import android.content.Context
import android.graphics.*
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewTreeObserver
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
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
    var random: Random

    init {
        View.inflate(context, R.layout.activity_game, this)
        this.presenter = Presenter(this)
        this.paint = Paint()
        paint.isAntiAlias = true
        this.random = Random()
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
        this.linearLayout = findViewById(R.id.layout_game)
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
        var randomNum: Int = random.nextInt(3) + 1
        var randomPosX: Int = random.nextInt(imageView.width / 5 + 800 - imageView.width / 5) + imageView.width / 5
        var randomPosY: Int = random.nextInt(imageView.height / 3 + 400 - imageView.width / 3) + imageView.width / 3
        for (i in 0..randomNum) {
            presenter.arrCustomer.add(Customer(randomPosX, randomPosY, randomPosX + 200, randomPosY + 200))
        }
        initiateCanvas()
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
                draw(canvas)
            }
        })
        imageView.invalidate()
    }

    override fun onClick(view: View?) {
        var counterBun: Int = presenter.arrBun.size
        var counterMeat: Int = presenter.arrMeat.size
        var counterSausage: Int = presenter.arrSausage.size
        if (view?.id == this.btnAddBun.id) {
            if (counterBun <= 3) {
                var bun = Bun(positionBun.get(counterBun % 3).left, positionBun.get(counterBun % 3).top, positionBun.get(counterBun % 3).right, positionBun.get(counterBun % 3).bottom)
                this.addBun(bun)
                this.positionBunFilled[counterBun % 3] = true
                bitmap = BitmapFactory.decodeResource(resources, R.drawable.bun)
                canvas.drawBitmap(bitmap, null, presenter.arrBun.get(counterBun % 3).rect, null)
            }

        } else if (view?.id == this.btnAddMeat.id) {
            if(counterMeat<=3){
                var meat = Meat(positionMeat.get(counterMeat % 3).left, positionMeat.get(counterMeat % 3).top, positionMeat.get(counterMeat % 3).right, positionMeat.get(counterMeat % 3).bottom)
                this.addMeat(meat)
                this.positionMeatFilled[counterBun%3] = true
                bitmap = BitmapFactory.decodeResource(resources, R.drawable.meat)
                canvas.drawBitmap(bitmap, null, presenter.arrMeat.get(counterMeat % 3).rect, null)
            }
        } else if (view?.id == this.btnAddSausage.id) {
            if(counterSausage<=3){
                var sausage = Sausage(positionSausage.get(counterSausage % 3).left, positionSausage.get(counterSausage % 3).top, positionSausage.get(counterSausage % 3).right, positionSausage.get(counterSausage % 3).bottom)
                this.addSausage(sausage)
                this.positionSausageFilled[counterSausage%3] = true
                bitmap = BitmapFactory.decodeResource(resources, R.drawable.sausage)
                canvas.drawBitmap(bitmap, null, presenter.arrSausage.get(counterSausage % 3).rect, null)
            }
        }
        imageView.invalidate()
    }

    override fun onTouch(view: View?, event: MotionEvent?): Boolean {
        var active: Food = Food(0, 0, 0, 0)

        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                for (item in presenter.arrBun) {
                    if (item.left <= event?.x.toInt() && item.top <= event?.y.toInt() && item.right >= event.x.toInt() && item.bottom >= event.y.toInt()) {
                        active = item

                        break
                    }
                }
                for (item in presenter.arrMeat) {
                    if (item.rect.contains(event?.x.toInt(), event?.y.toInt())) {
                        active = item
                        break
                    }
                }
                for (item in presenter.arrSausage) {
                    if (item.rect.contains(event?.x.toInt(), event?.y.toInt())) {
                        active = item
                        break
                    }
                }
                for (item in presenter.arrHotdog) {
                    if (item.rect.contains(event?.x.toInt(), event?.y.toInt())) {
                        active = item
                        break
                    }
                }
                for (item in presenter.arrBurger) {
                    if (item.rect.contains(event?.x.toInt(), event?.y.toInt())) {
                        active = item
                        break
                    }
                }
                Log.d("active : ", active.jenis)

                Log.d("active pos : ", active.left.toString() + " " + active.top.toString() + " " + active.right.toString() + " " + active.bottom.toString() + " ")

                Log.d("touch coordinate", event?.x.toString() + " + " + event?.y.toString())
                imageView.invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                if (active != null) {
                    active.rect.set(event.x.toInt() - 100, event.y.toInt() - 100, event.x.toInt() + 100, event.y.toInt() + 100)
                }
                Log.d("move : ",event?.x.toString() + " + " + event?.y.toString())
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                for (item in presenter.arrBun) {
                    if (Rect.intersects(active.rect,item.rect)) {
                        if(active.jenis.equals("meat")){
                            var burger = Burger(active as Meat,item,item.left,item.top,item.right,item.bottom)
                            this.presenter.addBurger(burger)
                            this.presenter.arrBun.remove(item)
                            this.presenter.arrMeat.remove(active as Meat)
                        }
                        break
                    }
                }
                invalidate()
            }
        }
        invalidate()
        return true
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        bitmap = BitmapFactory.decodeResource(resources, R.drawable.bun)
        for (items in presenter.arrBun) {
            canvas?.drawBitmap(bitmap, null, items.rect, null)
        }
        bitmap = BitmapFactory.decodeResource(resources, R.drawable.meat)
        for (items in presenter.arrMeat) {
            canvas?.drawBitmap(bitmap, null, items.rect, null)
        }
        bitmap = BitmapFactory.decodeResource(resources, R.drawable.burger)
        for (items in presenter.arrBurger) {
            canvas?.drawBitmap(bitmap, null, items.rect, null)
        }
        bitmap = BitmapFactory.decodeResource(resources, R.drawable.sausage)
        for (items in presenter.arrSausage) {
            canvas?.drawBitmap(bitmap, null, items.rect, null)
        }
        bitmap = BitmapFactory.decodeResource(resources, R.drawable.hotdog)
        for (items in presenter.arrHotdog) {
            canvas?.drawBitmap(bitmap, null, items.rect, null)
        }
        bitmap = BitmapFactory.decodeResource(resources, R.drawable.hotdog)
        for (items in presenter.arrHotdog) {
            canvas?.drawBitmap(bitmap, null, items.rect, null)
        }
        for (items in presenter.arrCustomer) {
            if (items.request == 0) {
                bitmap = BitmapFactory.decodeResource(resources, R.drawable.customer_burger)
                canvas?.drawBitmap(bitmap, null, items.rect, paint)
            } else {
                bitmap = BitmapFactory.decodeResource(resources, R.drawable.customer_hotdog)
                canvas?.drawBitmap(bitmap, null, items.rect, paint)
            }

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