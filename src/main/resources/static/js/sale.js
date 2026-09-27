

  // ==============================
  // DATA
  // ==============================

  const products = [
    { id: 1, name: "Espresso", category: "Cà phê", price: 35000, emoji: "☕" },
    { id: 2, name: "Americano", category: "Cà phê", price: 40000, emoji: "☕" },
    { id: 3, name: "Cappuccino", category: "Cà phê", price: 55000, emoji: "☕" },
    { id: 4, name: "Latte", category: "Cà phê", price: 55000, emoji: "🥛" },
    { id: 5, name: "Bạc xỉu", category: "Cà phê", price: 35000, emoji: "🧋" },
    { id: 6, name: "Cà phê sữa đá", category: "Cà phê", price: 30000, emoji: "🧋" },
    { id: 7, name: "Cold Brew", category: "Cà phê", price: 50000, emoji: "🧊" },
    { id: 8, name: "Trà đào cam sả", category: "Trà", price: 45000, emoji: "🍑" },
    { id: 9, name: "Trà vải", category: "Trà", price: 45000, emoji: "🍹" },
    { id: 10, name: "Trà matcha", category: "Trà", price: 50000, emoji: "🍵" },
    { id: 11, name: "Trà chanh", category: "Trà", price: 30000, emoji: "🍋" },
    { id: 12, name: "Trà sữa", category: "Trà", price: 45000, emoji: "🧋" },
    { id: 13, name: "Bánh croissant", category: "Bánh ngọt", price: 35000, emoji: "🥐" },
    { id: 14, name: "Tiramisu", category: "Bánh ngọt", price: 55000, emoji: "🍰" },
    { id: 15, name: "Cheesecake", category: "Bánh ngọt", price: 60000, emoji: "🍰" },
    { id: 16, name: "Muffin chocolate", category: "Bánh ngọt", price: 35000, emoji: "🧁" },
    { id: 17, name: "Nước cam", category: "Nước ép", price: 40000, emoji: "🍊" },
    { id: 18, name: "Nước ép dưa hấu", category: "Nước ép", price: 40000, emoji: "🍉" },
    { id: 19, name: "Sinh tố bơ", category: "Nước ép", price: 50000, emoji: "🥑" },
    { id: 20, name: "Sinh tố xoài", category: "Nước ép", price: 45000, emoji: "🥭" }
  ];

  function makeOrder(items) {
    return items.map(([id, qty]) => ({ productId: id, qty }));
  }

  const tables = Array.from({ length: 15 }, (_, i) => ({
    id: i + 1,
    status: "available",
    guests: 0,
    customer: "",
    phone: "",
    reservedAt: "",
    order: []
  }));

  // Mock orders: Tables 1, 3, 5 are occupied.
  tables[0].status = "occupied";
  tables[0].guests = 2;

  tables[2].status = "occupied";
  tables[2].guests = 3;

  tables[4].status = "occupied";
  tables[4].guests = 2;

  let selectedTableId = 1;
  let activeCategory = "Tất cả";
  let paymentMethod = "cash";
  let lastReceipt = null;

  const modal = id => ({ show: () => $('#' + id).modal('show'), hide: () => $('#' + id).modal('hide') });
  const getTable = id => tables.find(t => t.id === Number(id));
  const money = n => Math.round(n).toLocaleString("vi-VN") + "đ";

  function getProduct(id) {
    return products.find(p => p.id === id);
  }

   function openOrderPanelModal(){
    renderOrder();
    modal("orderPanelModal").show();
   }
  function orderTotal(table) {
    return table.order.reduce((sum, item) => {
      const p = getProduct(item.productId);
      return sum + (p ? p.price * item.qty : 0);
    }, 0);
  }

  function itemCount(table) {
    return table.order.reduce((sum, item) => sum + item.qty, 0);
  }

  function getStatusLabel(status) {
    return {
      available: "Trống",
      occupied: "Đang phục vụ",
      reserved: "Đã đặt"
    }[status];
  }

  function ensureOrder(table) {
    if (table.status === "available") {
      // Chuyển bàn sang trạng thái đang phục vụ nhưng giữ nguyên số khách đã chọn.
      table.status = "occupied";
      if (!Number(table.guests) || Number(table.guests) < 1) {
        table.guests = 1;
      }
    }
  }

  // ==============================
  // TABLE GRID
  // ==============================

  function renderTables() {
    const grid = document.getElementById("tableGrid");

    grid.innerHTML = tables.map(table => {
      const isSelected = table.id === selectedTableId;
      const total = orderTotal(table);

      return `
        <button class="table-card ${table.status} ${isSelected ? "selected" : ""}"
          onclick="selectTable(${table.id})">
          <i class="bi bi-cup-hot table-icon"></i>
          <div class="table-number">Bàn ${table.id}</div>
          <div class="table-status">
            <i class="bi bi-circle-fill" style="font-size:7px"></i>
            ${getStatusLabel(table.status)}
          </div>
          ${table.status === "occupied"
            ? `<div class="table-total">${money(total)}</div>
               <div class="table-guests"><i class="bi bi-people mr-1"></i>${table.guests} khách</div>`
            : table.status === "reserved"
              ? `<div class="table-total" style="font-size:12px">${escapeHTML(table.customer || "Khách đặt bàn")}</div>
                 <div class="table-guests"><i class="bi bi-people mr-1"></i>${table.guests} khách</div>`
              : `<div class="table-total muted">Chưa có đơn</div>
                 <div class="table-guests">Sẵn sàng phục vụ</div>`
          }
        </button>
      `;
    }).join("");

    updateSummary();
    const reservationBadge = document.getElementById("reservationBadge");
    if (reservationBadge) {
      reservationBadge.textContent = tables.filter(t => t.status === "reserved").length;
    }
  }

  function updateSummary() {
    const occupied = tables.filter(t => t.status === "occupied").length;
    const available = tables.filter(t => t.status === "available").length;
    const reserved = tables.filter(t => t.status === "reserved").length;

    document.getElementById("occupiedCount").textContent = occupied;
    document.getElementById("availableCount").textContent = available;
    document.getElementById("reservedCount").textContent = reserved;
  }

  function selectTable(id) {
    selectedTableId = Number(id);
<!--    renderTables();-->
    openOrderPanelModal();
    renderOrder();
  }

  // ==============================
  // ORDER
  // ==============================

  function renderOrder() {
    const table = getTable(selectedTableId);
    if (!table) return;

    document.getElementById("orderTableTitle").textContent = "Bàn " + table.id;
    document.getElementById("orderGuestInfo").textContent =
      table.guests + " khách";

    const guestCount = document.getElementById("guestCount");
    if (guestCount) {
      guestCount.textContent = (Number(table.guests) || 1) + " khách";
    }

    document.getElementById("orderCode").textContent =
      table.status === "occupied" ? "#ORD-" + String(table.id).padStart(3, "0") : "Chưa có đơn";

    const status = document.getElementById("orderStatus");
    status.textContent = getStatusLabel(table.status);
    status.className = "badge rounded-pill " +
      (table.status === "occupied" ? "badge-danger" :
       table.status === "reserved" ? "badge-warning" : "badge-success");

    const reservedAction = document.getElementById("reservedTableAction");
    if (reservedAction) {
      if (table.status === "reserved") {
        reservedAction.style.display = "block";
        reservedAction.innerHTML = `
          <div class="rounded p-3" style="background:#fff8e5;border:1px solid #f2d78c">
            <div class="d-flex align-items-start  mb-3">
              <i class="bi bi-calendar-check h4" style="color:#b88715"></i>
              <div class="flex-grow-1">
                <div class="font-weight-bold">Bàn đã được đặt trước</div>
                <div class="small mt-1">${escapeHTML(table.customer || "Khách đặt bàn")} · ${Number(table.guests) || 1} khách</div>
                <div class="small muted mt-1">${escapeHTML(table.phone || "Chưa có số điện thoại")}</div>
                <div class="small muted mt-1">${table.reservedAt ? escapeHTML(new Date(table.reservedAt).toLocaleString("vi-VN")) : "Chưa xác định giờ đặt"}</div>
              </div>
            </div>
            <button type="button" class="primary-btn w-100" onclick="checkInReservation(${table.id})">
              <i class="bi bi-check-circle mr-2"></i> Nhận bàn này
            </button>
          </div>`;
      } else {
        reservedAction.style.display = "none";
        reservedAction.innerHTML = "";
      }
    }

    const items = document.getElementById("orderItems");

    if (!table.order.length) {
      items.innerHTML = `
        <div class="empty-order">
          <i class="bi bi-bag"></i>
          <div class="font-weight-bold mb-2">Chưa có món nào</div>
          <div class="small">Thêm món vào đơn hàng để bắt đầu phục vụ.</div>
          ${table.status === "reserved" ? `<div class="small mt-2">Khách đặt: ${escapeHTML(table.customer)}</div>` : ""}
        </div>`;
    } else {
      items.innerHTML = table.order.map(item => {
        const p = getProduct(item.productId);
        if (!p) return "";

        return `
          <div class="order-item">
            <div class="item-image">${p.emoji}</div>
            <div class="item-info">
              <div class="item-name">${escapeHTML(p.name)}</div>
              <div class="item-price">${money(p.price)}</div>
              <div class="qty-control">
                <button class="qty-btn" onclick="changeQty(${p.id},-1)">
                  <i class="bi bi-dash"></i>
                </button>
                <span class="qty-number">${item.qty}</span>
                <button class="qty-btn" onclick="changeQty(${p.id},1)">
                  <i class="bi bi-plus"></i>
                </button>
              </div>
            </div>
            <div class="item-total">${money(p.price * item.qty)}</div>
          </div>`;
      }).join("");
    }

    const total = orderTotal(table);
    document.getElementById("subtotal").textContent = money(total);
    document.getElementById("discount").textContent = money(0);
    document.getElementById("total").textContent = money(total);

<!--    renderTables();-->
  }

  function changeQty(productId, delta) {
    const table = getTable(selectedTableId);
    if (table.status === "reserved") {
      showToast("Vui lòng nhận bàn trước khi thêm món.");
      return;
    }

    ensureOrder(table);

    const item = table.order.find(i => i.productId === productId);

    if (item) {
      item.qty += delta;
      if (item.qty <= 0) {
        table.order = table.order.filter(i => i.productId !== productId);
      }
    } else if (delta > 0) {
      table.order.push({ productId, qty: 1 });
    }

    renderOrder();
  }

  function changeGuestCount(delta) {
    const table = getTable(selectedTableId);
    if (!table) return;

    if (table.status === "reserved") {
      showToast("Vui lòng nhận bàn trước khi chỉnh số khách.");
      return;
    }

    let guests = Number(table.guests) || 1;
    guests += delta;
    guests = Math.max(1, Math.min(50, guests));

    table.guests = guests;
    renderOrder();
  }

  // ==============================
  // MENU
  // ==============================

  function openMenu() {
    const table = getTable(selectedTableId);
    if (table.status === "reserved") {
      showToast("Vui lòng nhận bàn trước khi thêm món.");
      return;
    }

    document.getElementById("productSearch").value = "";
    renderCategories();
    renderProducts();
    modal("menuModal").show();
  }

  function renderCategories() {
    const categories = ["Tất cả", ...new Set(products.map(p => p.category))];

    document.getElementById("categoryList").innerHTML = categories.map(cat => `
      <button class="category-btn ${activeCategory === cat ? "active" : ""}"
        onclick="setCategory('${cat}')">${cat}</button>
    `).join("");
  }

  function setCategory(category) {
    activeCategory = category;
    renderCategories();
    renderProducts();
  }

  function renderProducts() {
    const search = document.getElementById("productSearch").value.trim().toLowerCase();
    const table = getTable(selectedTableId);

    const filtered = products.filter(p =>
      (activeCategory === "Tất cả" || p.category === activeCategory) &&
      p.name.toLowerCase().includes(search)
    );

    document.getElementById("productGrid").innerHTML = filtered.length
      ? filtered.map(p => {
          const currentItem = table?.order?.find(i => i.productId === p.id);
          const currentQty = currentItem ? currentItem.qty : 0;

          return `
            <div class="product-card">
              <div class="product-emoji">${p.emoji}</div>
              <div class="product-name">${escapeHTML(p.name)}</div>
              <div class="product-price">${money(p.price)}</div>
              <div class="muted small mt-1 mb-3">${escapeHTML(p.category)}</div>
              <div class="d-flex align-items-center justify-content-center ">
                <button type="button" class="qty-btn"
                        onclick="changeMenuQty(${p.id}, -1)"
                        ${currentQty === 0 ? "disabled" : ""}>
                  <i class="bi bi-dash"></i>
                </button>
                <span class="font-weight-bold text-center" id="menu-qty-${p.id}" style="min-width:32px">${currentQty}</span>
                <button type="button" class="qty-btn" onclick="changeMenuQty(${p.id}, 1)">
                  <i class="bi bi-plus"></i>
                </button>
              </div>
            </div>
          `;
        }).join("")
      : `<div class="muted text-center py-5" style="grid-column:1/-1">Không tìm thấy món phù hợp.</div>`;
  }

  function changeMenuQty(productId, delta) {
    const table = getTable(selectedTableId);
    if (!table) return;

    if (table.status === "reserved") {
      showToast("Vui lòng nhận bàn trước khi thêm món.");
      return;
    }

    ensureOrder(table);

    let item = table.order.find(i => i.productId === productId);

    if (!item && delta > 0) {
      item = { productId, qty: 1 };
      table.order.push(item);
    } else if (item) {
      item.qty += delta;
      if (item.qty <= 0) {
        table.order = table.order.filter(i => i.productId !== productId);
      }
    }

    renderOrder();
    renderProducts();
  }

  function addProduct(productId) {
    changeMenuQty(productId, 1);
  }

  // ==============================
  // RESERVATION
  // ==============================

  function openReserve() {
    const select = document.getElementById("reserveTable");
    const available = tables.filter(t => t.status === "available");

    select.innerHTML = available.map(t =>
      `<option value="${t.id}" ${t.id === selectedTableId ? "selected" : ""}>Bàn ${t.id}</option>`
    ).join("");

    if (!available.length) {
      showToast("Không còn bàn trống để đặt.");
      return;
    }

    document.getElementById("reserveForm").reset();
    document.getElementById("reserveGuests").value = 2;
    modal("reserveModal").show();
  }

  document.getElementById("reserveForm").addEventListener("submit", e => {
    e.preventDefault();

    const id = Number(document.getElementById("reserveTable").value);
    const table = getTable(id);

    if (!table || table.status !== "available") {
      showToast("Bàn không còn trống.");
      return;
    }

    table.status = "reserved";
    table.customer = document.getElementById("reserveName").value.trim();
    table.phone = document.getElementById("reservePhone").value.trim();
    table.guests = Number(document.getElementById("reserveGuests").value) || 1;
    table.reservedAt = document.getElementById("reserveTime").value;

    selectedTableId = id;
    modal("reserveModal").hide();
    renderOrder();
    showToast("Đặt bàn thành công.");
  });

  // ==============================
  // NHẬN BÀN ĐÃ ĐẶT
  // ==============================

  function openReservations() {
<!--    document.getElementById("reservationSearch").value = "";-->
<!--    renderReservations();-->
    modal("reservationsModal").show();
  }

<!--  function renderReservations() {-->
<!--    const keyword = (document.getElementById("reservationSearch")?.value || "")-->
<!--      .trim().toLowerCase();-->
<!--    const reservedTables = tables.filter(t => t.status === "reserved");-->
<!--    const filtered = reservedTables.filter(t =>-->
<!--      [String(t.id), t.customer, t.phone]-->
<!--        .some(value => String(value || "").toLowerCase().includes(keyword))-->
<!--    );-->

<!--    document.getElementById("reservationBadge").textContent = reservedTables.length;-->
<!--    document.getElementById("reservationCount").textContent = reservedTables.length + " bàn";-->

<!--    const list = document.getElementById("reservationList");-->
<!--    if (!filtered.length) {-->
<!--      list.innerHTML = `-->
<!--        <div class="text-center py-5">-->
<!--          <i class="bi bi-calendar-check display-5 d-block mb-3" style="color:#c8cadd"></i>-->
<!--          <div class="font-weight-bold mb-2">${reservedTables.length ? "Không tìm thấy lượt đặt phù hợp" : "Không có bàn nào đang chờ nhận"}</div>-->
<!--          <div class="muted small">${reservedTables.length ? "Thử tìm theo tên khách, số điện thoại hoặc số bàn." : "Các bàn được đặt trước sẽ xuất hiện tại đây."}</div>-->
<!--        </div>`;-->
<!--      return;-->
<!--    }-->

<!--    list.innerHTML = filtered.map(t => `-->
<!--      <div class="border rounded p-3 mb-3">-->
<!--        <div class="d-flex flex-wrap justify-content-between align-items-start  mb-3">-->
<!--          <div>-->
<!--            <div class="font-weight-bold h6">${escapeHTML(t.customer || "Khách đặt bàn")}</div>-->
<!--            <div class="muted small mt-1">Bàn ${t.id} · ${t.guests} khách</div>-->
<!--          </div>-->
<!--          <span class="badge badge-warning">Chờ nhận bàn</span>-->
<!--        </div>-->
<!--        <div class="row g-2 small mb-3">-->
<!--          <div class="col-md-6"><i class="bi bi-telephone mr-2 muted"></i>${escapeHTML(t.phone || "Chưa có số điện thoại")}</div>-->
<!--          <div class="col-md-6"><i class="bi bi-clock mr-2 muted"></i>${t.reservedAt ? escapeHTML(new Date(t.reservedAt).toLocaleString("vi-VN")) : "Chưa xác định giờ đặt"}</div>-->
<!--        </div>-->
<!--        <div class="d-flex flex-wrap ">-->
<!--          <button class="outline-btn flex-fill" onclick="selectTable(${t.id}); modal('reservationsModal').hide();">-->
<!--            <i class="bi bi-eye mr-1"></i> Xem bàn-->
<!--          </button>-->
<!--          <button class="primary-btn flex-fill" onclick="checkInReservation(${t.id})">-->
<!--            <i class="bi bi-check-circle mr-1"></i> Nhận bàn-->
<!--          </button>-->
<!--        </div>-->
<!--      </div>-->
<!--    `).join("");-->
<!--  }-->

  function checkInReservation(tableId) {
    const table = getTable(tableId);
    if (!table || table.status !== "reserved") {
      showToast("Bàn này không còn ở trạng thái đã đặt.");
      renderReservations();
<!--      renderTables();-->
      return;
    }

    table.status = "occupied";
    // Giữ lại thông tin khách và số lượng khách; đơn hàng bắt đầu trống.
    table.order = [];
    selectedTableId = table.id;

    modal("reservationsModal").hide();
    renderOrder();
    showToast("Đã nhận bàn " + table.id + " cho khách " + (table.customer || "đặt bàn") + ".");
  }

  // ==============================
  // TRANSFER
  // ==============================

  function openTransfer() {
    const source = getTable(selectedTableId);

    if (source.status !== "occupied") {
      showToast("Chỉ có thể chuyển bàn đang phục vụ.");
      return;
    }

    const targets = tables.filter(t => t.id !== source.id && t.status === "available");
    if (!targets.length) {
      showToast("Không có bàn trống để chuyển đến.");
      return;
    }

    document.getElementById("transferFrom").value = "Bàn " + source.id;
    document.getElementById("transferTo").innerHTML = targets.map(t =>
      `<option value="${t.id}">Bàn ${t.id}</option>`
    ).join("");

    modal("transferModal").show();
  }

  function transferTable() {
    const source = getTable(selectedTableId);
    const target = getTable(document.getElementById("transferTo").value);

    if (!source || !target || target.status !== "available") {
      showToast("Bàn đích không hợp lệ.");
      return;
    }

    target.status = "occupied";
    target.order = source.order;
    target.guests = source.guests;
    target.customer = source.customer;

    source.status = "available";
    source.order = [];
    source.guests = 0;
    source.customer = "";
    source.phone = "";

    selectedTableId = target.id;
    modal("transferModal").hide();
    renderOrder();
    showToast("Chuyển bàn thành công.");
  }

  // ==============================
  // MERGE
  // ==============================

  function openMerge() {
    const target = getTable(selectedTableId);

    if (target.status !== "occupied") {
      showToast("Bàn nhận phải đang có đơn hàng.");
      return;
    }

    const sources = tables.filter(t =>
      t.id !== target.id && t.status === "occupied"
    );

    if (!sources.length) {
      showToast("Không có bàn nào đang phục vụ để gộp.");
      return;
    }

    document.getElementById("mergeTarget").value = "Bàn " + target.id;
    document.getElementById("mergeSource").innerHTML = sources.map(t =>
      `<option value="${t.id}">Bàn ${t.id} - ${money(orderTotal(t))}</option>`
    ).join("");

    modal("mergeModal").show();
  }

  function mergeTable() {
    const target = getTable(selectedTableId);
    const source = getTable(document.getElementById("mergeSource").value);

    if (!source || !target || source.status !== "occupied" || target.status !== "occupied") {
      showToast("Không thể gộp hai bàn này.");
      return;
    }

    source.order.forEach(item => {
      const existing = target.order.find(i => i.productId === item.productId);
      if (existing) existing.qty += item.qty;
      else target.order.push({ ...item });
    });

    target.guests += source.guests;

    source.status = "available";
    source.order = [];
    source.guests = 0;
    source.customer = "";
    source.phone = "";

    modal("mergeModal").hide();
    renderOrder();
    showToast("Gộp bàn thành công.");
  }

  // ==============================
  // SPLIT
  // ==============================

  function openSplit() {
    const source = getTable(selectedTableId);

    if (source.status !== "occupied" || !source.order.length) {
      showToast("Bàn chưa có đơn hàng để tách.");
      return;
    }

    const targets = tables.filter(t =>
      t.id !== source.id && t.status === "available"
    );

    if (!targets.length) {
      showToast("Không có bàn trống để tách.");
      return;
    }

    document.getElementById("splitTarget").innerHTML = targets.map(t =>
      `<option value="${t.id}">Bàn ${t.id}</option>`
    ).join("");

    document.getElementById("splitItems").innerHTML = source.order.map(item => {
      const p = getProduct(item.productId);

      return `
        <div class="d-flex align-items-center justify-content-between  py-3 border-bottom">
          <div>
            <div class="font-weight-bold">${escapeHTML(p.name)}</div>
            <small class="muted">${money(p.price)} · Tối đa ${item.qty} món</small>
          </div>
          <input type="number" min="0" max="${item.qty}" value="0"
            class="form-control split-qty" data-id="${p.id}" style="width:90px">
        </div>`;
    }).join("");

    modal("splitModal").show();
  }

  function splitTable() {
    const source = getTable(selectedTableId);
    const target = getTable(document.getElementById("splitTarget").value);

    if (!target || target.status !== "available") {
      showToast("Bàn đích không hợp lệ.");
      return;
    }

    const selections = [...document.querySelectorAll(".split-qty")];
    const moves = selections.map(input => ({
      productId: Number(input.dataset.id),
      qty: Number(input.value) || 0
    }));

    if (!moves.some(item => item.qty > 0)) {
      showToast("Vui lòng chọn ít nhất một món để tách.");
      return;
    }

    for (const move of moves) {
      const original = source.order.find(i => i.productId === move.productId);
      if (move.qty < 0 || move.qty > original.qty) {
        showToast("Số lượng tách không hợp lệ.");
        return;
      }
    }

    target.status = "occupied";
    target.guests = 1;
    target.order = [];

    moves.forEach(move => {
      if (move.qty <= 0) return;

      const original = source.order.find(i => i.productId === move.productId);
      original.qty -= move.qty;
      target.order.push({ productId: move.productId, qty: move.qty });
    });

    source.order = source.order.filter(item => item.qty > 0);

    if (!source.order.length) {
      source.status = "available";
      source.guests = 0;
    }

    selectedTableId = target.id;
    modal("splitModal").hide();
    renderOrder();
    showToast("Tách bàn thành công.");
  }

  // ==============================
  // CLEAR TABLE
  // ==============================

  function clearTable() {
    const table = getTable(selectedTableId);

    if (table.status === "available") {
      showToast("Bàn đang trống.");
      return;
    }

    modal("confirmModal").show();
  }

  function confirmClear() {
    const table = getTable(selectedTableId);

    table.status = "available";
    table.order = [];
    table.guests = 0;
    table.customer = "";
    table.phone = "";
    table.reservedAt = "";

    modal("confirmModal").hide();
    renderOrder();
    showToast("Đã hủy bàn.");
  }

  // ==============================
  // PAYMENT
  // ==============================

  function openPayment() {
    const table = getTable(selectedTableId);

    if (table.status !== "occupied" || !table.order.length) {
      showToast("Bàn chưa có đơn hàng để thanh toán.");
      return;
    }

    paymentMethod = "cash";
    document.getElementById("paymentTotal").textContent = money(orderTotal(table));
    document.getElementById("cashReceived").value = orderTotal(table);
    document.getElementById("cashFields").style.display = "block";

    document.querySelectorAll(".payment-method").forEach(btn => {
      btn.classList.toggle("active", btn.dataset.method === "cash");
      btn.style.borderColor = btn.dataset.method === "cash" ? "var(--primary)" : "";
      btn.style.color = btn.dataset.method === "cash" ? "var(--primary)" : "";
    });

    updateChange();
    modal("paymentModal").show();
  }

  function setPaymentMethod(method, btn) {
    paymentMethod = method;

    document.querySelectorAll(".payment-method").forEach(b => {
      b.classList.remove("active");
      b.style.borderColor = "";
      b.style.color = "";
    });

    btn.classList.add("active");
    btn.style.borderColor = "var(--primary)";
    btn.style.color = "var(--primary)";

    const cashFields = document.getElementById("cashFields");
    cashFields.style.display = method === "cash" ? "block" : "none";

    if (method !== "cash") {
      document.getElementById("cashReceived").value = "";
    } else {
      document.getElementById("cashReceived").value = orderTotal(getTable(selectedTableId));
    }

    updateChange();
  }

  function setCash(amount) {
    document.getElementById("cashReceived").value = amount;
    updateChange();
  }

  function updateChange() {
    const total = orderTotal(getTable(selectedTableId));
    const received = Number(document.getElementById("cashReceived").value) || 0;

    document.getElementById("changeAmount").textContent =
      money(Math.max(0, received - total));
  }

  function completePayment() {
    const table = getTable(selectedTableId);
    const total = orderTotal(table);
    const received = Number(document.getElementById("cashReceived").value) || 0;

    if (paymentMethod === "cash" && received < total) {
      showToast("Số tiền khách đưa chưa đủ.");
      return;
    }

    if (!table.order.length) {
      showToast("Đơn hàng không có món.");
      return;
    }

    lastReceipt = {
      tableId: table.id,
      items: table.order.map(item => ({ ...item })),
      total,
      method: paymentMethod,
      time: new Date(),
      received: paymentMethod === "cash" ? received : total,
      change: paymentMethod === "cash" ? received - total : 0
    };

    renderReceipt(lastReceipt);

    table.status = "available";
    table.order = [];
    table.guests = 0;
    table.customer = "";
    table.phone = "";

    modal("paymentModal").hide();
    renderOrder();
    showToast("Thanh toán thành công.");

    setTimeout(() => modal("receiptModal").show(), 250);
  }

  // ==============================
  // RECEIPT
  // ==============================

  function renderReceipt(receipt) {
    const methodLabels = {
      cash: "Tiền mặt",
      card: "Thẻ",
      transfer: "Chuyển khoản"
    };

    const itemRows = receipt.items.map(item => {
      const p = getProduct(item.productId);

      return `
        <div class="receipt-row">
          <span>${escapeHTML(p.name)} x${item.qty}</span>
          <span>${money(p.price * item.qty)}</span>
        </div>`;
    }).join("");

    document.getElementById("receiptContent").innerHTML = `
      <div class="receipt">
        <h4>BEAN & BREW</h4>
        <div class="receipt-center">COFFEE SHOP</div>
        <div class="receipt-center mt-1">Hóa đơn thanh toán</div>
        <div class="receipt-line"></div>

        <div>Bàn: ${receipt.tableId}</div>
        <div>Mã đơn: ORD-${String(receipt.tableId).padStart(3, "0")}</div>
        <div>Thời gian: ${receipt.time.toLocaleString("vi-VN")}</div>

        <div class="receipt-line"></div>
        ${itemRows}
        <div class="receipt-line"></div>

        <div class="receipt-row">
          <span>Tạm tính</span>
          <span>${money(receipt.total)}</span>
        </div>
        <div class="receipt-row">
          <strong>TỔNG CỘNG</strong>
          <strong>${money(receipt.total)}</strong>
        </div>
        <div class="receipt-row">
          <span>Thanh toán</span>
          <span>${methodLabels[receipt.method]}</span>
        </div>
        <div class="receipt-row">
          <span>Khách đưa</span>
          <span>${money(receipt.received)}</span>
        </div>
        <div class="receipt-row">
          <span>Tiền thừa</span>
          <span>${money(receipt.change)}</span>
        </div>

        <div class="receipt-line"></div>
        <div class="receipt-center mt-3">Cảm ơn quý khách!</div>
        <div class="receipt-center">Hẹn gặp lại ❤️</div>
      </div>`;
  }

  function printReceipt() {
    const table = getTable(selectedTableId);

    if (table.status !== "occupied" || !table.order.length) {
      if (lastReceipt) {
        renderReceipt(lastReceipt);
        modal("receiptModal").show();
      } else {
        showToast("Không có hóa đơn để in.");
      }
      return;
    }

    const receipt = {
      tableId: table.id,
      items: table.order.map(item => ({ ...item })),
      total: orderTotal(table),
      method: "cash",
      time: new Date(),
      received: orderTotal(table),
      change: 0
    };

    renderReceipt(receipt);
    modal("receiptModal").show();
  }

  // ==============================
  // UTILITIES
  // ==============================

  function escapeHTML(value) {
    return String(value ?? "").replace(/[&<>"']/g, char => ({
      "&": "&amp;",
      "<": "&lt;",
      ">": "&gt;",
      '"': "&quot;",
      "'": "&#39;"
    })[char]);
  }

  function showToast(message) {
    document.getElementById("toastMessage").textContent = message;
    $('#appToast').toast({ delay: 2500 });
    $('#appToast').toast('show');
  }

  // INITIALIZE
<!--  renderTables();-->
  renderOrder();

