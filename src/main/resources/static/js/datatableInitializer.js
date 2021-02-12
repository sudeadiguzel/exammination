function getProp(object, keys, defaultVal) {
    keys = Array.isArray(keys) ? keys : keys.split('.');
    object = object[keys[0]];
    if (object && keys.length > 1) {
        return getProp(object, keys.slice(1), defaultVal);
    }
    return object ? object : defaultVal;
}

var DatatableUtils = {
    initializer: function (selector, options) {
        console.log(options);
        $(selector).DataTable(
            Object.assign({
                    language: {
                        "sDecimal": ",",
                        "sEmptyTable": "Tabloda herhangi bir veri mevcut değil",
                        "sInfo": "_TOTAL_ kayıttan _START_ - _END_ arasındaki kayıtlar gösteriliyor",
                        "sInfoEmpty": "Kayıt yok",
                        "sInfoFiltered": "(_MAX_ kayıt içerisinden bulunan)",
                        "sInfoPostFix": "",
                        "sInfoThousands": ".",
                        "sLengthMenu": "Sayfada _MENU_ kayıt göster",
                        "sLoadingRecords": "Yükleniyor...",
                        "sProcessing": "İşleniyor...",
                        "sSearch": "Ara:",
                        "sZeroRecords": "Eşleşen kayıt bulunamadı",
                        "oPaginate": {
                            "sFirst": "İlk",
                            "sLast": "Son",
                            "sNext": "Sonraki",
                            "sPrevious": "Önceki"
                        },
                        "oAria": {
                            "sSortAscending": ": artan sütun sıralamasını aktifleştir",
                            "sSortDescending": ": azalan sütun sıralamasını aktifleştir"
                        },
                        "select": {
                            "rows": {
                                "_": "%d kayıt seçildi",
                                "0": "",
                                "1": "1 kayıt seçildi"
                            }
                        }
                    },
                    processing: false,
                    serverSide: true,
                    searchDelay: 300,
                }
                , options));
    },
    renderRoute: function (url, idParam = 'id', dataParam) {
        return function (data, type, row) {
            let label = (dataParam == undefined || (typeof row[dataParam] === "string" && row[dataParam].trim() === "")) ? data : row[dataParam];
            if (typeof label === "string" && label.trim().length === 0) {
                label = '&lt;İsim Belirsiz&gt;';
            }

            return '' + url + '<a href="/">' + getProp(row, idParam, '') + '' + label + '</a>';
        };
    },
    renderBoolean: function (trueContent = 'Evet', falseContent = 'Hayır', dataParam) {
        return function (data, type, row) {
            data = dataParam != undefined ? row[dataParam] : data;
            return data ? trueContent : falseContent;
        }
    },
    renderBooleanReverse: function (trueContent = 'Evet', falseContent = 'Hayır', dataParam) {
        return DatatableUtils.renderBoolean(falseContent, trueContent, dataParam);
    },
    mergeStrings: function (strings = [], merger = ' ') {
        return function (data, type, row) {
            if(Array.isArray(strings)) {
                return strings.map(param => row[param]).join(merger);
            }

            return data;
        };
    },
    renderActions: function (url = '', actions = ['edit', 'remove'], idParam = 'id') {
        return function (data, type, row) {
            if (!Array.isArray(actions)) {
                return data;
            }

            return actions.reduce(function (buttons, action) {
                switch (action) {
                    case 'edit':
                        buttons += "<a href='/' data-toggle='tooltip' title='Düzenle'><i class='fa fa-pencil text-info'><" + url + "/"+row[idParam]+"i></a> ";
                        break;

                    case 'remove':
                        buttons += "<form class='d-inline-block delete-form' method='POST' action='" + url + "/delete/" + row[idParam] + "'>" +
                            "<input type=\"hidden\" name=\"" + csrfParameter + "\" value=\"" + csrfToken + "\" />" +
                            "<button type='submit' class='text-danger bg-transparent border-0' data-toggle='tooltip' title='Kaldır'>" +
                            "<i class='fa fa-trash'></i></button>" +
                            "</form> ";
                        break;
                    case 'verify':
                        buttons += "<form class='d-inline-block verify-form' method='POST' action='" + url + "/verify/" + row[idParam] + "'>" +
                            "<input type=\"hidden\" name=\"" + csrfParameter + "\" value=\"" + csrfToken + "\" />" +
                            "<button type='submit' class='text-primary bg-transparent border-0' data-toggle='tooltip' title='Onayla'>" +
                            "<i class='fa fa-check'></i></button>" +
                            "</form> ";
                        break;
                }

                return buttons;
            }, "");
        };
    },
    renderStatus: function() {
        return function (data) {
            if(data === 'PASSIVE') {
                return '<span class="badge badge-warning badge-pill">Pasif</span>';
            }

            if(data === 'DELETED') {
                return '<span class="badge badge-danger badge-pill">Silinmiş</span>';
            }

            return '<span class="badge badge-success badge-pill">Aktif</span>';
        }
    },
    renderVerificationStatus: function() {
        return function (data) {

            return data.verified===true ? 'Onaylandı' : (data.waitingVerify ===true  ? 'Onay Bekliyor' : 'Taslak');
        }
    },
    renderProperty: function(propertyName = null) {
        return function (data, type, row) {
            return propertyName !== undefined && row[propertyName] !== undefined ? row[propertyName] : data;
        }
    },
    renderLink: function (urlParam, labelParam) {
        return function (data, type, row) {
            return '<a href="' + (urlParam === undefined ? data : row[urlParam]) + '">' + (labelParam == undefined ? data : row[labelParam]) + '</a>';
        };
    },
}