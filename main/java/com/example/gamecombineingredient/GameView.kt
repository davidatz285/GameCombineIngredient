package com.example.gamecombineingredient

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.*
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import java.util.*

class GameView(context: Context) : LinearLayout(context), InterfaceGameActivity, View.OnClickListener, View.OnTouchListener {
    var active: Food
    var bmMeat: Bitmap
    var bmSausage: Bitmap
    var bmBun: Bitmap
    var bmBurger: Bitmap
    var bmHotdog: Bitmap
    var bmCusBurger: Bitmap
    var bmCusHotdog: Bitmap
    lateinit var canvas: Canvas
    val presenter: Presenter
    val paint: Paint
    var imageView: ImageView
    lateinit var bitmap: Bitmap
    var btnAddMeat: Button
    var btnAddBun: Button
    var btnAddSausage: Button
    var linearLayout: LinearLayout
    var random: Random
    var score = 0

    init {
        View.inflate(context, R.layout.activity_game, this)
        this.presenter = Presenter(this)
        this.paint = Paint()
        paint.textSize = 100f
        paint.isAntiAlias = true
        this.random = Random()
        this.active = Food(0, 0, 0, 0)
        this.btnAddMeat = findViewById(R.id.btn_add_meat)
        this.btnAddBun = findViewById(R.id.btn_add_bun)
        this.btnAddSausage = findViewById(R.id.btn_add_sausage)
        this.imageView = findViewById(R.id.ivCanvas)
        this.imageView.setOnTouchListener(this)
        this.linearLayout = findViewById(R.id.layout_game)
        this.btnAddBun.setOnClickListener(this)
        this.btnAddMeat.setOnClickListener(this)
        this.btnAddSausage.setOnClickListener(this)
        this.presenter.positionSausage.add(Position(imageView.width / 5 + 100, imageView.height / 3 + 600, imageView.width / 5 + 300, imageView.height / 3 + 800))
        this.presenter.positionSausage.add(Position(imageView.width / 5 + 100, imageView.height / 3 + 900, imageView.width / 5 + 300, imageView.height / 3 + 1100))
        this.presenter.positionSausage.add(Position(imageView.width / 5 + 100, imageView.height / 3 + 1200, 2 * imageView.width / 5 + 300, imageView.height / 3 + 1400))
        this.presenter.positionBun.add(Position(imageView.width / 5 + 400, imageView.height / 3 + 600, imageView.width / 5 + 600, imageView.height / 3 + 800))
        this.presenter.positionBun.add(Position(imageView.width / 5 + 400, imageView.height / 3 + 900, imageView.width / 5 + 600, imageView.height / 3 + 1100))
        this.presenter.positionBun.add(Position(imageView.width / 5 + 400, imageView.height / 3 + 1200, 2 * imageView.width / 5 + 600, imageView.height / 3 + 1400))
        this.presenter.positionMeat.add(Position(imageView.width / 5 + 800, imageView.height / 3 + 600, imageView.width / 5 + 1000, imageView.height / 3 + 800))
        this.presenter.positionMeat.add(Position(imageView.width / 5 + 800, imageView.height / 3 + 900, imageView.width / 5 + 1000, imageView.height / 3 + 1100))
        this.presenter.positionMeat.add(Position(imageView.width / 5 + 800, imageView.height / 3 + 1200, 2 * imageView.width / 5 + 1000, imageView.height / 3 + 1400))
        this.presenter.positionCustomer.add(Position(imageView.width / 5 + 100, imageView.height / 3 + 200, imageView.width / 5 + 300, imageView.height / 3 + 400))
        this.presenter.positionCustomer.add(Position(imageView.width / 5 + 400, imageView.height / 3 + 200, imageView.width / 5 + 600, imageView.height / 3 + 400))
        this.presenter.positionCustomer.add(Position(imageView.width / 5 + 800, imageView.height / 3 + 200, imageView.width / 5 + 1000, imageView.height / 3 + 400))
        this.bmBun = BitmapFactory.decodeResource(resources, R.drawable.bun)
        this.bmMeat = BitmapFactory.decodeResource(resources, R.drawable.meat)
        this.bmSausage = BitmapFactory.decodeResource(resources, R.drawable.sausage)
        this.bmBurger = BitmapFactory.decodeResource(resources, R.drawable.burger)
        this.bmHotdog = BitmapFactory.decodeResource(resources, R.drawable.hotdog)
        this.bmCusBurger = BitmapFactory.decodeResource(resources, R.drawable.customer_burger)
        this.bmCusHotdog = BitmapFactory.decodeResource(resources, R.drawable.customer_hotdog)
        var randomNum: Int = random.nextInt(2) + 1
        for (i in 0..randomNum) {
            presenter.arrCustomer.add(Customer(this.presenter.positionCustomer.get(i).left, this.presenter.positionCustomer.get(i).top, this.presenter.positionCustomer.get(i).right, this.presenter.positionCustomer.get(i).bottom))
        }

        initiateCanvas()
        setWillNotDraw(false)
    }


    fun initiateCanvas() {
        bitmap = Bitmap.createBitmap(1080, 1440, Bitmap.Config.ARGB_8888)
        imageView.setImageBitmap(bitmap)
        canvas = Canvas(bitmap)
        /*var vto: ViewTreeObserver = linearLayout.viewTreeObserver
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
        })*/
        imageView.invalidate()
    }

    override fun onClick(view: View?) {
        var counterBun: Int = presenter.arrBun.size
        var counterMeat: Int = presenter.arrMeat.size
        var counterSausage: Int = presenter.arrSausage.size
        if (view?.id == this.btnAddBun.id) {
            if (counterBun <= 3) {
                var bun = Bun(this.presenter.positionBun.get(counterBun % 3).left, this.presenter.positionBun.get(counterBun % 3).top, this.presenter.positionBun.get(counterBun % 3).right, this.presenter.positionBun.get(counterBun % 3).bottom)
                this.addBun(bun)
                this.presenter.positionBunFilled[counterBun % 3] = true

            }
            invalidate()
        } else if (view?.id == this.btnAddMeat.id) {
            if (counterMeat <= 3) {
                var meat = Meat(this.presenter.positionMeat.get(counterMeat % 3).left, this.presenter.positionMeat.get(counterMeat % 3).top, this.presenter.positionMeat.get(counterMeat % 3).right, this.presenter.positionMeat.get(counterMeat % 3).bottom)
                this.addMeat(meat)
                this.presenter.positionMeatFilled[counterBun % 3] = true
            }
            invalidate()
        } else if (view?.id == this.btnAddSausage.id) {
            if (counterSausage <= 3) {
                var sausage = Sausage(this.presenter.positionSausage.get(counterSausage % 3).left, this.presenter.positionSausage.get(counterSausage % 3).top, this.presenter.positionSausage.get(counterSausage % 3).right, this.presenter.positionSausage.get(counterSausage % 3).bottom)
                this.addSausage(sausage)
                this.presenter.positionSausageFilled[counterSausage % 3] = true
            }
            invalidate()
        }

    }

    override fun onTouch(view: View?, event: MotionEvent?): Boolean {


        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                active = presenter.getTouchedFood(event.x.toInt(), event.y.toInt())
                imageView.invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                active.left = event.x.toInt() - 100
                active.top = event.y.toInt() - 100
                active.right = event.x.toInt() + 100
                active.bottom = event.y.toInt() + 100
                active.rect.set(event.x.toInt() - 100, event.y.toInt() - 100, event.x.toInt() + 100, event.y.toInt() + 100)

                Log.d("active : ", active.jenis)
                Log.d("active pos : ", active.left.toString() + " " + active.top.toString() + " " + active.right.toString() + " " + active.bottom.toString() + " ")
                Log.d("touch coordinate", event?.x.toString() + " + " + event?.y.toString())


                Log.d("move : ", event?.x.toString() + " + " + event?.y.toString())
                for (item in presenter.arrBun) {
                    if (Rect.intersects(active.rect, item.rect)) {
                        if (!active.equals(item) && (active.jenis.equals("meat") || active.jenis.equals("bun"))) {
                            var burger = Burger(active as Meat, item, item.left, item.top, item.right, item.bottom)
                            this.presenter.addBurger(burger)
                            this.presenter.arrBun.remove(item)
                            this.presenter.arrMeat.remove(active as Meat)
                            score += 5
                            Log.d("intersect", "burger")
                        } else if (!active.equals(item) && (active.jenis.equals("sausage") || active.jenis.equals("bun"))) {
                            var hotdog = Hotdog(active as Sausage, item, item.left, item.top, item.right, item.bottom)
                            this.presenter.addHotdog(hotdog)
                            this.presenter.arrBun.remove(item)
                            this.presenter.arrSausage.remove(active as Sausage)
                            score += 5
                        }
                        break
                    }
                }
                for (item in presenter.arrCustomer) {
                    if (Rect.intersects(active.rect, item.rect)) {
                        if (!active.equals(item) && active.jenis.equals("hotdog")) {
                            if (item.fillRequest(active) < 0) {
                                score += item.fillRequest(active)
                                this.presenter.arrCustomer.remove(item)
                                this.presenter.arrHotdog.remove(active)
                            } else {
                                score += item.fillRequest(active)
                                this.presenter.arrHotdog.remove(active)
                                this.presenter.arrCustomer.remove(item)
                            }

                        } else if (!active.equals(item) && active.jenis.equals("burger")) {
                            if (item.fillRequest(active) < 0) {
                                score += item.fillRequest(active)
                                this.presenter.arrCustomer.remove(item)
                                this.presenter.arrBurger.remove(active)
                            } else {
                                score += item.fillRequest(active)
                                this.presenter.arrBurger.remove(active)
                                this.presenter.arrCustomer.remove(item)
                            }
                        }
                    }
                }
                imageView.invalidate()
            }
            MotionEvent.ACTION_UP -> {

                /*if (presenter.arrCustomer.isEmpty()) {
                    showDialog()
                }*/
                imageView.invalidate()
            }
        }
        invalidate()
        return true
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        canvas?.drawText(score.toString(), width / 2f, 100f, paint)
        for (items in presenter.arrBun) {
            canvas?.drawBitmap(bmBun, null, items.rect, null)
        }
        for (items in presenter.arrMeat) {
            canvas?.drawBitmap(bmMeat, null, items.rect, null)
        }
        for (items in presenter.arrBurger) {
            canvas?.drawBitmap(bmBurger, null, items.rect, null)
        }
        for (items in presenter.arrSausage) {
            canvas?.drawBitmap(bmSausage, null, items.rect, null)
        }
        for (items in presenter.arrHotdog) {
            canvas?.drawBitmap(bmHotdog, null, items.rect, null)
        }

        for (items in presenter.arrCustomer) {
            if (items.request == 0) {
                canvas?.drawBitmap(bmCusBurger, null, items.rect, null)
            } else if (items.request == 1) {
                canvas?.drawBitmap(bmCusHotdog, null, items.rect, null)
            }
        }
    }

    fun showDialog() {
        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setTitle("Selamat!")
        alertDialogBuilder.setMessage("Anda berhasil menyelesaikan level ini! \n Skor anda adalah " + this.score + "\n Mau main lagi?")
        alertDialogBuilder.setPositiveButton("YES", DialogInterface.OnClickListener() { dialogInterface: DialogInterface, i: Int ->
            fun onClick(dialog: DialogInterface, which: Int) {
                val intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent)
            }

        })
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